package pl.polsl.gawron.marcel.paintoonServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.polsl.gawron.marcel.paintoonData.models.Color;
import pl.polsl.gawron.marcel.paintoonData.models.HistoryEntry;
import pl.polsl.gawron.marcel.paintoonData.models.Image;
import pl.polsl.gawron.marcel.paintoonServer.repositories.HistoryEntryRepository;

import java.util.List;

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

    /**
     * Insert latest set pixels for every pixel in bitmap
     *
     * @param image                  bitmap object
     * @param historyEntryRepository database access class
     * @return class responsible for setup of bitmap
     */
    @Bean
    public SetupImage createSetup(Image image, HistoryEntryRepository historyEntryRepository) {
        return new SetupImage(image, historyEntryRepository);
    }

    /**
     * Class responsbile for setup of the bitmap
     *
     * @author Marcel Gawron
     * @version 1.0
     */
    public class SetupImage {

        /**
         * Construct class instance
         *
         * @param image                  reference to bitmap in memory wrapper class
         * @param historyEntryRepository class responsible for handling storage of history entries in database
         */
        public SetupImage(Image image, HistoryEntryRepository historyEntryRepository) {
            reloadImageHistory(image, historyEntryRepository);
        }

        /**
         * Insert latest pixel changes into the in-memory bitmap
         *
         * @param image                  reference to bitmap in memory wrapper class
         * @param historyEntryRepository class responsible for handling storage of history entries in database
         */
        public void reloadImageHistory(Image image, HistoryEntryRepository historyEntryRepository) {
            List<HistoryEntry> historyEntries = historyEntryRepository.getLatestPixelChangeForEachPixel();
            for (var entry : historyEntries) {
                image.setPixel(entry.getX(), entry.getY(), new Color(entry.getRedComponent(), entry.getGreenComponent(), entry.getBlueComponent()));
            }
            System.out.println("Pixels were restored");
        }
    }

}
