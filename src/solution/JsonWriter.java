package solution;

import java.util.Map;

public class JsonWriter
{
    /**
     * Converts a Map<String, Object> into a JSON-formatted string.
     * Only handles strings and primitive types (e.g., numbers, booleans).
     * Strings are properly escaped according to JSON format.
     *
     * @param map the input map containing key-value pairs to convert to JSON
     * @return a JSON-formatted string representation of the map
     */
    public static String toJson(Map<String, Object> map)
    {
        StringBuilder json = new StringBuilder(); // Initialize a StringBuilder to build the JSON string
        json.append("{"); // Begin the JSON object with an opening brace
        boolean first = true; // Used to determine if a comma is needed before appending a new key-value pair

        // Iterate over each entry in the map
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            if (!first)
            {
                json.append(","); // Append a comma before the next key-value pair if it's not the first
            }

            // Append the key, enclosed in double quotes
            json.append("\"").append(entry.getKey()).append("\":");

            Object value = entry.getValue();

            // If the value is a String, escape it and enclose in double quotes
            if (value instanceof String string)
            {
                json.append("\"").append(escapeJson(string)).append("\"");
            }
            else
            {
                // For non-string values (numbers, booleans), append as is
                json.append(value);
            }

            first = false; // After the first iteration, all further entries will need a preceding comma
        }

        json.append("}"); // Close the JSON object with a closing brace
        return json.toString(); // Return the complete JSON string
    }

    /**
     * Escapes special characters in a JSON string according to JSON standards.
     * Handles characters like backslashes, quotes, and control characters.
     *
     * @param input the raw string to be escaped
     * @return the escaped string safe for JSON formatting
     */
    private static String escapeJson(String input)
    {
        return input.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\b", "\\b")
                    .replace("\f", "\\f")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}
