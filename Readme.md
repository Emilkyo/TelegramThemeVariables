# Variable Parser Tool For Telegram Themes For Android

This very tiny project gets up-to-date variables for custom Telegram themes for Android from Telegram App
[source code](https://github.com/DrKLO/Telegram).

## Description

The `FindVariables` application performs the following functions:

- Downloads ThemeColors.java class from [Telegram GitHub repository](https://github.com/DrKLO/Telegram)
- Locates a specific method in the file
- Extracts values in quotation marks from the method
- Writes the extracted values to a separate file

## Usage

1. Ensure you have the JDK and a Java IDE installed to run the Java code.
2. Download the source code from this repository.
3. Run the `FindVariables` application.

## Instructions

1. **downloadFile:** Downloads a Java file from GitHub using the provided URL and saves it in the specified directory.
2. **readInFile:** Searches for a method in the file based on the provided method name and returns lines from the
   method.
3. **parseData:** Extracts lines in quotation marks from the list of strings.
4. **writeToFile:** Writes the values to a file in the `out.txt` format.

## Example Output

Actual variables will be in the file out.txt

```
parser.zip/ 
├─ 📁VarriablesGetter/ 
│  ├─ 📁parse/ 
│  │  ├─ ThemeColors.java
│  │  ├─ out.txt
│  ├─ 📁src/
│  │  ├─ .../

```

## Dependencies

- Java 8 or higher
- Internet connectivity to download the file from GitHub

## Connect

If you encounter any issues or have questions about the project, please contact me on [telegram]().

## License

This project is distributed under the MIT License. See the [LICENSE](./LICENSE) file for more detailed information.
