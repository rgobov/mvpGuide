package gobov.roma.mvpguide.services;

import gobov.roma.mvpguide.repository.MediaRepository;
import gobov.roma.mvpguide.repository.PointOfInterestRepository;
import gobov.roma.mvpguide.model.Media;
import gobov.roma.mvpguide.model.PointOfInterest;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {
    private final MinioClient minioClient;
    private final MediaRepository mediaRepository;
    private final PointOfInterestRepository poiRepository;
    @Value("${minio.bucket-name}")
    private String bucketName;
    @Value("${minio.url}")
    private String minioUrl;

    public Media uploadMedia(Long poiId, MultipartFile file, String mediaType) {
        PointOfInterest poi = poiRepository.findById(poiId)
                .orElseThrow(() -> new NotFoundException("POI not found with ID: " + poiId));

        String fileName = generateFileName(file.getOriginalFilename());
        String url = uploadToMinIO(file, fileName);

        Media media = Media.builder()
                .pointOfInterest(poi)
                .type(mediaType)
                .url(url)
                .size(file.getSize())
                .build();

        return mediaRepository.save(media);
    }

    private String uploadToMinIO(MultipartFile file, String fileName) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build());
            return String.format("%s/%s/%s", minioUrl, bucketName, fileName);
        } catch (Exception e) {
            throw new FileUploadException("Failed to upload file", e);
        }
    }

    private String generateFileName(String originalFileName) {
        String extension = originalFileName != null && originalFileName.contains(".")
                ? originalFileName.substring(originalFileName.lastIndexOf("."))
                : "";
        return UUID.randomUUID().toString() + extension;
    }
}

// Custom exception for resource not found
class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

// Custom exception for file upload failures
class FileUploadException extends RuntimeException {
    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}