package solution;

/**
 * Handles user authentication, registration, and validation
 * Implements user credential management with strict validation rules
 */
public class Login 
{
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellPhoneNumber;
    private boolean isLoggedIn;
    
    /**
     * Default constructor initializes with empty values
     */
    public Login() 
    {
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.cellPhoneNumber = "";
        this.isLoggedIn = false;
    }
    
    /**
     * Parameterized constructor for user registration
     * @param username User's unique identifier
     * @param password User's secret phrase
     * @param firstName User's given name
     * @param lastName User's family name
     * @param cellPhoneNumber User's contact number in international format
     */
    public Login(String username, String password, String firstName, 
                String lastName, String cellPhoneNumber) 
    {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellPhoneNumber = cellPhoneNumber;
        this.isLoggedIn = false;
    }
    
    /**
     * Validates username format requirements
     * @return true if username contains underscore and is ≤5 characters
     */
    public boolean checkUserName() 
    {
        return username != null && 
               username.contains("_") && 
               username.length() <= 5;
    }
    
    /**
     * Validates password complexity requirements
     * @return true if password has:
     * - ≥8 characters
     * - 1 uppercase
     * - 1 number
     * - 1 special character
     */
    public boolean checkPasswordComplexity() 
    {
        if (password == null || password.length() < 8) 
        {
            return false;
        }
        
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        
        for (char c : password.toCharArray()) 
        {
            if (Character.isUpperCase(c)) 
            {
                hasCapital = true;
            } 
            else if (Character.isDigit(c)) 
            {
                hasNumber = true;
            } 
            else if (!Character.isLetterOrDigit(c)) 
            {
                hasSpecial = true;
            }
            
            // Early exit if all requirements met
            if (hasCapital && hasNumber && hasSpecial) 
            {
                return true;
            }
        }
        
        return hasCapital && hasNumber && hasSpecial;
    }
    
    /**
     * Validates South African cell number format
     * @return true if format matches +27[0-9]{9}
     */
    public boolean checkCellPhoneNumber() 
    {
        return cellPhoneNumber != null && 
               cellPhoneNumber.matches("^\\+27\\d{9}$");
    }
    
    /**
     * Performs complete user registration validation
     * @return Registration success message or specific error
     */
    public String registerUser() 
    {
        if (!checkUserName()) 
        {
            return "Username must contain underscore and be ≤5 characters";
        }
        
        if (!checkPasswordComplexity()) 
        {
            return "Password must have ≥8 chars with uppercase, number and special char";
        }
        
        if (!checkCellPhoneNumber()) 
        {
            return "Cell number must be in +27XXXXXXXXX format";
        }
        
        return "Registration successful!";
    }
    
    /**
     * Authenticates user credentials
     * @param username Attempted username
     * @param password Attempted password
     * @return true if credentials match stored values
     */
    public boolean loginUser(String username, String password) 
    {
        boolean credentialsMatch = this.username.equals(username) && 
                                 this.password.equals(password);
        this.isLoggedIn = credentialsMatch;
        return credentialsMatch;
    }
    
    /**
     * Generates user login status message
     * @return Personalized welcome or error message
     */
    public String getLoginStatus() 
    {
        if (isLoggedIn) 
        {
            return String.format("Welcome %s %s, great to see you!", 
                               firstName, lastName);
        }
        return "Username or password incorrect";
    }
    
    // Standard getters and setters with validation
    public String getUsername() 
    { 
        return username; 
    }
    
    public void setUsername(String username) 
    { 
        this.username = username; 
    }
    
    public String getPassword() 
    { 
        return password; 
    }
    
    public void setPassword(String password) 
    { 
        this.password = password; 
    }
    
    public String getFirstName() 
    { 
        return firstName; 
    }
    
    public void setFirstName(String firstName) 
    { 
        this.firstName = firstName; 
    }
    
    public String getLastName() 
    { 
        return lastName; 
    }
    
    public void setLastName(String lastName) 
    { 
        this.lastName = lastName; 
    }
    
    public String getCellPhoneNumber() 
    { 
        return cellPhoneNumber; 
    }
    
    public void setCellPhoneNumber(String cellPhoneNumber) 
    { 
        this.cellPhoneNumber = cellPhoneNumber; 
    }
    
    public boolean isLoggedIn() 
    { 
        return isLoggedIn; 
    }
    
    public void setLoggedIn(boolean loggedIn) 
    { 
        this.isLoggedIn = loggedIn; 
    }
}