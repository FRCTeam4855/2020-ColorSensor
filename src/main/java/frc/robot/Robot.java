/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
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

    if(detectedColor.red >= 0.34 && detectedColor.green <=.4) {
      SmartDashboard.putString("Color", "Red");
      pastColor = currentColor;
      currentColor = COLOR.Red;
    } 
     if(detectedColor.green >= 0.52) {
        SmartDashboard.putString("Color", "Green");
        pastColor = currentColor;
        currentColor = COLOR.Green;
  } 
   if(detectedColor.blue >= 0.335 && detectedColor.green <= 1 ) {
        SmartDashboard.putString("Color", "Blue");
        pastColor = currentColor;
        currentColor = COLOR.Blue;
   }
    if(detectedColor.blue >= 0.13 && detectedColor.green <= 0.55 && detectedColor.red >= 0.285 && detectedColor.red <= .4) {
        SmartDashboard.putString("Color", "Yellow");
        pastColor = currentColor;
        currentColor = COLOR.Yellow;
    } 
    if (currentColor != pastColor && pastColor != COLOR.None) {
      // The color has changed, add to rotation count
      partialTurn ++;
      if(partialTurn > 7) {
        rotation ++;
        partialTurn = 0; 
      }

    }
   /* if(currentColor == COLOR.Red && pastColor != Red) {
      partialTurn ++;
      if(partialTurn > 7) {
        rotation ++;
      }
    }

    if(currentColor == Red && pastColor == Green)  {
      partialTurn ++;
      if(partialTurn > 7) {
        rotation ++;
      }
    }

     if(currentColor == Blue && pastColor == Green) {
      partialTurn ++;
      if(partialTurn > 7) {
        rotation ++;
      }
    }

    if(currentColor == Blue && pastColor == Yellow) {
      partialTurn ++;
      if(partialTurn > 7) {
        rotation ++;
      }
    }

     if(currentColor == Green && pastColor == Blue) {
      partialTurn ++;
      if(partialTurn > 7) {
        rotation ++;
        partialTurn = 0;
      }
    }

    if(currentColor == Green && pastColor ==  Red)  {
      partialTurn ++;
      if(partialTurn > 7) {
        rotation ++;
        partialTurn = 0;
      }
    }

    if(currentColor == Yellow && pastColor == Red) {
      partialTurn ++;
      if(partialTurn > 7) {
        rotation ++;
        partialTurn = 0;
      }
    }
    if(currentColor ==  Yellow && pastColor == Blue)  {
      partialTurn ++;
      if(partialTurn > 7) {
        rotation ++;
        partialTurn = 0;
      }
    }
  
     
    */
  
  

//SmartDashboard.putString("Color");
    
  double proximity = m_colorSensor.getProximity();

    SmartDashboard.putNumber("Proximity", proximity);
  }

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
