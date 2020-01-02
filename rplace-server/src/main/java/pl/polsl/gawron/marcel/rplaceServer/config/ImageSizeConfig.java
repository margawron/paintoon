package pl.polsl.gawron.marcel.rplaceServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.polsl.gawron.marcel.rplaceData.models.Image;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Configuration
public class ImageSizeConfig {

    @Bean
    public Image setImageSize() {
        return new Image(700);
    }
}
