/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

import com.revrobotics.ColorSensorV3;

public class Robot extends TimedRobot {

  public final I2C.Port i2cPort = I2C.Port.kMXP;
  public final I2C.Port i2cPort2 = I2C.Port.kOnboard; 
  public final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  public final ColorSensorV3 m_colourSensor = new ColorSensorV3(i2cPort2);
  int rotation = 0; 
  int partialTurn = 0;
  int Failsafe = 0;
  boolean B = false;
  int Numberball = 0;
  boolean Fs2 = false;
  boolean Cellseen = false;

  Joystick Controller = new Joystick(1);

  public enum COLOR {
    Red, Yellow, Blue, Green, None , PowerCELL
  }
  COLOR currentColor = COLOR.None; 
  COLOR currentColour = COLOR.None; // the color the that sensor is
  COLOR pastColor = COLOR.None;     // that color the the sensor was
  Color detectedColor;              // the color that the sensor is actively reading
  Color detectedColour;
  Spark motor = new Spark(0);
  
  @Override
  public void robotInit() {
  }
 
  @Override
  public void robotPeriodic() {

    detectedColor = m_colorSensor.getColor();
    detectedColour = m_colourSensor.getColor();
    SmartDashboard.putNumber("Rotations", rotation);
    SmartDashboard.putNumber("Red", detectedColour.red);
    SmartDashboard.putNumber("Green", detectedColour.green);
    SmartDashboard.putNumber("Blue", detectedColour.blue);
    SmartDashboard.putNumber("Proximity", proximity);
    SmartDashboard.putNumber("Color Changes", partialTurn);
    SmartDashboard.putNumber("PowerCellCounter", Numberball);
  

    if(detectedColor.red >= 0.34 && detectedColor.green <=.45) {
      SmartDashboard.putString("Color", "Red");
      pastColor = currentColor;
      currentColor = COLOR.Red;
    } 
     if(detectedColor.green >= 0.50 && detectedColor.red <= .23) {
        SmartDashboard.putString("Color", "Green");
        pastColor = currentColor;
        currentColor = COLOR.Green;
  } 
   if(detectedColor.blue >= 0.345 && detectedColor.green <= .50 ) {
        SmartDashboard.putString("Color", "Blue");
        pastColor = currentColor;
        currentColor = COLOR.Blue;
   }
    if(detectedColor.blue >= 0.14 && detectedColor.green >= .52 && detectedColor.green <= 0.54 && detectedColor.red >= 0.29 && detectedColor.red <= .31) {
        SmartDashboard.putString("Color", "Yellow");
        pastColor = currentColor;
        currentColor = COLOR.Yellow;
    } 
      if (currentColor != pastColor && pastColor != COLOR.None) {
      partialTurn ++;
      }
      
      if(partialTurn > 7) {
        rotation ++;
        partialTurn = 0;
      }

      /*if(currentColor != COLOR.None && currentColor != COLOR.PowerCELL) {
        Fs2 = true;
      } */
      if(detectedColour.blue >= 0.11 && detectedColour.green >= .45 && detectedColour.green <= 0.55 && detectedColour.red >= 0.28 && detectedColour.red <= .34 && Fs2 == false) {
        currentColor = COLOR.PowerCELL; 
        Numberball ++;
        Cellseen = true;
      } 
      if(Cellseen = true) {
        Fs2 = true;
        SmartDashboard.putBoolean("PowerCell?", true);
      }
      if(detectedColour.blue <=.11 || detectedColour.green <= .45 || detectedColour.green >= .55 || detectedColour.red <= .28 || detectedColour.red >= .34){
        Fs2 = false;
        Cellseen = false;
        SmartDashboard.putBoolean("PowerCell?", false);
      }
    }
    double proximity = m_colorSensor.getProximity();

   
  

  @Override
  public void autonomousInit() {
    
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
   
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Controller.getRawButton(2);
    if(Controller.getRawButton(2) == true && Failsafe == 0) {
     motor.set(1);
    }
    if(Controller.getRawButton(2) == false && Failsafe == 0) {
      motor.set(0);
     }
    String gameData;
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    if(gameData.length() > 0)
    {
      switch (gameData.charAt(0))
      {
        case 'B' :
         if (rotation == 3 && currentColor == COLOR.Red){
          rotation = 0; 
          partialTurn = 0;
          motor.set(0);
          Failsafe = 1;
          }
          break;
        case 'G' :
        if (rotation == 3 && currentColor == COLOR.Yellow){
          rotation = 0; 
          partialTurn = 0;
          motor.set(0);
          Failsafe = 1;
          }
          break;
        case 'R' :
        if (rotation == 3 && currentColor == COLOR.Blue){
          rotation = 0; 
          partialTurn = 0;
          motor.set(0);
          Failsafe = 1;
          }
          break;
        case 'Y' :
        if (rotation == 3 && currentColor == COLOR.Green){
          rotation = 0; 
          partialTurn = 0; 
          motor.set(0);
          Failsafe = 1;
          }
          break;
        default :
          //This is corrupt data
          break;
      }
    } else {
      //Code for no data received yet
    } 
  }


  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
