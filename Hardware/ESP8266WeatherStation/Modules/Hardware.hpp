/**
 * @name ESP8266WeatherStation
 * @file Hardware.h
 * @class Hardware
 * @author Sándor Balázs - AZA6NL
 * @date 2022.10.27.
 * @brief The hardware descriptor and initializator.
 * Contact: sandorbalazs9402@gmail.com
 */
#pragma once

/**
 * @brief Hardvare description class for initialisation and global access.
 */
class Hardware {
public:
  /**
   * @brief OLED display width in pixels.
   */
  const uint8_t screenWith 128;

  /**
   * @brief OLED display height in pixels.
   */
  const uint8_t screenHieght 64;

  /**
   * @brief OLED display Reset pin (or -1 if sharing Arduino reset pin).
   */
  const int8_t -1;

  /**
   * @brief OLED display i2c address.
   */
  const uint8_t screenI2CAddress 0x3C;

  /**
   * @brief Hardvare constructor.
   */
  Hardware();

  /**
   * @brief Hardvare destructor.
   */
  ~Hardware();

  /**
   * @brief Hardware prepare.
   */
  setup();
}