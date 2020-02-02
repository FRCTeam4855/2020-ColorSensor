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

import com.revrobotics.ColorSensorV3;

public class Robot extends TimedRobot {

  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  int rotation = 0;
  int partialTurn = 0;

  public enum COLOR {
    Red, Yellow, Blue, Green, None
  }
  COLOR currentColor = COLOR.None;  // the color that the sensor was
  COLOR pastColor = COLOR.None;     // the color that the sensor is
  Color detectedColor;              // the color that the sensor is actively reading
  Spark motor = new Spark(0);

  @Override
  public void robotInit() {
  }

  @Override
  public void robotPeriodic() {

    detectedColor = m_colorSensor.getColor();

    SmartDashboard.putNumber("Rotations", rotation);
    SmartDashboard.putNumber("Color Changes", partialTurn);
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Proximity", proximity);
    
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
      motor.set(.1);

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
    String gameData;
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    if(gameData.length() > 0)
    {
      switch (gameData.charAt(0))
      {
        case 'B' :
      
       if (rotation == 3 && currentColor == COLOR.Red){
       motor.set(0);
       rotation = 0; 
       }
      
       SmartDashboard.putNumber("Color Changes", partialTurn);
          break;
        case 'G' :
        if (rotation == 3 && currentColor == COLOR.Yellow){
          motor.set(0);
          rotation = 0; 
          }
          break;
        case 'R' :
        if (rotation == 3 && currentColor == COLOR.Blue){
          motor.set(0);
          rotation = 0; 
          }
          break;
        case 'Y' :
        if (rotation == 3 && currentColor == COLOR.Green){
          motor.set(0);
          rotation = 0; 
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
