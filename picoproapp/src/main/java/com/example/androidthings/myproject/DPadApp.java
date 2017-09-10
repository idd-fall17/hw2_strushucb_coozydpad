package com.example.androidthings.myproject;
import com.google.android.things.pio.Gpio;

/**
 * Originally created by bjoern on 8/29/17.
 * Modified by strushucb afterwards to add the DPad functionality
 * Defines 6 buttons (a DPad, "Enter", and "Delete"). The 6 buttons
 * allow the user to navigate the rows and columns of three rows of characters,
 * selecting letters to build a message.
 */

public class DPadApp extends SimplePicoPro {
  /* Mapping of buttons to GPIO pins! */
  private Gpio buttonUp = GPIO_172;
  private Gpio buttonDown = GPIO_174;
  private Gpio buttonLeft = GPIO_173;
  private Gpio buttonRight = GPIO_10;
  private Gpio buttonEnter = GPIO_32;
  private Gpio buttonDelete = GPIO_175;

  //instance of the graphical display
  AlphabetSelectorView alphabetSelectorView;
  //keep track of time for debouncing
  private long lastTime;
  private final long debounceTime = 100;

  //currently highlighted column and row
  private int column;
  private int row;
  //the resulting message
  private String message;
  private char[][] key_map = new char[][]{
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
    //Sets the PinModes and the EdgeTriggers
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

  //translate - translates the button presses into adjustments of the model.
  //Direction buttons will change the row or column, Enter or Delete
  //will add or remove characters from the resulting message.
  //Note - the columns and rows will wrap around.
  private void translate(Gpio button) {
    //if "left" is pressed, decrease column
    if (button == buttonLeft) {
      column--;
        // wrap to the end of the row if needed
      if (column < 0) column = key_map[row].length - 1;

    //if "right" is pressed, increase the column
    } else if (button == buttonRight) {
      column++;
        //wrap to beginning of row if needed
      if (column > key_map[row].length - 1) column = 0;

    //if "down" is pressed, increase the row
    } else if (button == buttonDown) {
      row++;
      //wrap to first row if needed
      if (row > key_map.length - 1) row = 0;
      //adjust the column for an odd number of characters
      if (column > key_map[row].length - 1) column--;

     //if "up" is pressed, decrease the row
    } else if (button == buttonUp) {
      row--;
        //wrap to bottom row if needed
      if (row < 0) row = key_map.length - 1;
        //adjust the column for an odd number of characters
      if (column > key_map[row].length - 1) column--;

    //if "Enter" is pressed, add selected character to the message
    } else if (button == buttonEnter) {
      message = message + key_map[row][column];
    //if "Delete" is pressed, remove last character from message
    } else if (button == buttonDelete) {
      if(message.length() > 0)
        message = message.substring(0,message.length()-1);
    }
  }

  @Override
  void digitalEdgeEvent(Gpio pin, boolean value) {
    long nowTime = System.currentTimeMillis();
    //simple debouncing via time comparison
    if((nowTime - lastTime) > debounceTime) {

      if (value == LOW) {     //button was pressed
        translate(pin);       //adjust the model
                              //update the view
        alphabetSelectorView.setHighlight(row, column);
        alphabetSelectorView.setMessage(message);
        lastTime = nowTime;

      } else {              //value == HIGH, button released
        lastTime = nowTime;
      }
    }
  }
}