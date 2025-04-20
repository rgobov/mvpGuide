package gobov.roma.mvpguide.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        try {
            MinioClient client = MinioClient.builder()
                    .endpoint(minioUrl)
                    .credentials(accessKey, secretKey)
                    .build();
            // Проверка подключения
            client.listBuckets(); // Вызов для проверки соединения
            return client;
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize MinioClient: " + e.getMessage(), e);
        }
    }
}