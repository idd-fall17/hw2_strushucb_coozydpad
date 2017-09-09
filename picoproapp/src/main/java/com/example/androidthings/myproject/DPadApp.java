package com.example.androidthings.myproject;
import com.google.android.things.pio.Gpio;

/**
 * Created by bjoern on 8/29/17.
 * Uses the Rainbow HAT - blinks the red LED above pads A,B,C when corresponding pad is pressed
 */

public class DPadApp extends SimplePicoPro {
  /* Mapping of buttons and LEDs to GPIO pins */
  Gpio buttonUp = GPIO_172;
  Gpio buttonDown = GPIO_174;
  Gpio buttonLeft = GPIO_173;
  Gpio buttonRight = GPIO_10;
  Gpio buttonEnter = GPIO_32;
  Gpio buttonDelete = GPIO_175;


  AlphabetSelectorView alphabetSelectorView;
  long lastTime;
  long debounceTime = 100;
  int column;
  int row;
  String message;
  char[][] key_map = new char[][]{
          {'A', 'B', 'C','D', 'E', 'F', 'G', 'H', 'I'},
          {'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q','R'},
          {'S', 'T', 'U','V', 'W', 'X', 'Y', 'Z',' '}
  };

  public DPadApp(AlphabetSelectorView result){
    super();
    this.alphabetSelectorView = result;
    column = 0;
    row = 0;
    message = "";
  }
  @Override
  public void setup() {
    pinMode(buttonUp,Gpio.DIRECTION_IN);
    pinMode(buttonDown,Gpio.DIRECTION_IN);
    pinMode(buttonLeft,Gpio.DIRECTION_IN);
    pinMode(buttonRight,Gpio.DIRECTION_IN);
    pinMode(buttonEnter,Gpio.DIRECTION_IN);
    pinMode(buttonDelete,Gpio.DIRECTION_IN);

    setEdgeTrigger(buttonUp,Gpio.EDGE_BOTH);
    setEdgeTrigger(buttonDown,Gpio.EDGE_BOTH);
    setEdgeTrigger(buttonLeft,Gpio.EDGE_BOTH);
    setEdgeTrigger(buttonRight,Gpio.EDGE_BOTH);
    setEdgeTrigger(buttonEnter,Gpio.EDGE_BOTH);
    setEdgeTrigger(buttonDelete,Gpio.EDGE_BOTH);

  }

  @Override
  public void loop() {
  } //do nothing here

  private void translate(Gpio button) {
    if (button == buttonLeft) {
      column--;
      if (column < 0) column = key_map[row].length - 1;
    } else if (button == buttonRight) {
      column++;
      if (column > key_map[row].length - 1) column = 0;
    } else if (button == buttonDown) {
      row++;
      if (row > key_map.length - 1) row = 0;
      if (column > key_map[row].length - 1) column--;
    } else if (button == buttonUp) {
      row--;
      if (row < 0) row = key_map.length - 1;
      if (column > key_map[row].length - 1) column--;
    } else if (button == buttonEnter) {
      message = message + key_map[row][column];
    } else if (button == buttonDelete) {
      if(message.length() > 0)
        message = message.substring(0,message.length()-1);
    }
  }

  //https://programmingelectronics.com/tutorial-19-debouncing-a-button-with-arduino-old-version/

  @Override
  void digitalEdgeEvent(Gpio pin, boolean value) {
    long nowTime = System.currentTimeMillis();
    if((nowTime - lastTime) > debounceTime) {
      if (value == LOW) {     //button was pressed
        translate(pin);
        alphabetSelectorView.setHighlight(row, column);
        alphabetSelectorView.setMessage(message);
        lastTime = nowTime;
      } else {                //value == HIGH, button released
        lastTime = nowTime;
        //delay(5000);
      }
    }
  }
}