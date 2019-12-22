package pl.polsl.gawron.marcel.rplace.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pl.polsl.gawron.marcel.rplace.models.Color;

public class ColorTest {

    @Test
    public void testIterator(){
        // Given
        Color color = new Color(100,102,103);
        // When
        int index = 0;
        // Colors are given in the following order BLUE GREEN RED
        // Then
        for(var component : color){
            switch (index){
                case 0:{
                    assertEquals((byte)103,component.byteValue(),"Blue should be the value of 103");
                    break;
                }
                case 1:{
                    assertEquals((byte)102,component.byteValue(),"Green should be of value 102");
                    break;
                }
                case 3:{
                    assertEquals((byte)100, component.byteValue(), "Red should be of value 100");
                    break;
                }
            }
            index++;
        }
    }

}
