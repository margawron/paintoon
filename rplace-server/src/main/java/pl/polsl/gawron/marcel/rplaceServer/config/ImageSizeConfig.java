package pl.polsl.gawron.marcel.rplaceServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.polsl.gawron.marcel.rplaceData.models.Image;

/**
 * Spring config
 *
 * @author Marcel Gawron
 * @version 1.0
 */
@Configuration
public class ImageSizeConfig {

    /**
     * Proper constructor needs to be provided for Spring to know
     * how to construct Image model
     *
     * @return Image object (size of canvas is 16x16)
     */
    @Bean
    public Image setImageSize() {
        return new Image(16);
    }
}
