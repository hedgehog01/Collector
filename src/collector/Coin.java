package collector;

/**
 * Class that represents a Coin.
 *
 * @author Nathan Randelman
 * @version 08.09.2014
 */
public class Coin extends CollectableItem {

    //instance variables

    private String _faceValue;
    private CoinGrade _grade;
    private CoinCurrency _currency;
    
    private final String unknown = "UNKNOWN";

    //========================================================================================//
    //Constructors
    //========================================================================================//
    /**
     * Default constructor - sets Unique Coin ID. Name ,facevalue and grade are
     * set to default values.
     */
    public Coin() {
        super();
        _grade = CoinGrade.UNKNOWN;
        _faceValue = unknown;
        _currency = CoinCurrency.UNKNOWN;
    }

    /**
     * Constructor that sets coin name. Grade set to default.
     *
     * @param name the coin name
     */
    public Coin(String name) {
        super(name);
        _grade = CoinGrade.UNKNOWN;
        _faceValue = unknown;
        _currency = CoinCurrency.UNKNOWN;
    }

    /**
     * Constructor that sets coin name. Grade set to default.
     *
     * @param name the coin name
     * @param grade the Coin grade
     */
    public Coin(String name, CoinGrade grade) {
        super(name);
        setGrade(grade);
    }

    /**
     * Constructor that sets coin name. Grade set to default.
     *
     * @param name the coin name.
     * @param grade the Coin grade.
     * @param facevalue the coin face value.
     * @param currency the coin currency.
     */
    public Coin(String name, CoinGrade grade, String facevalue, CoinCurrency currency, StringBuilder note) {
        this (name,grade);
        setFaceValue(facevalue);
        setCurrency(currency);
        setItemNote(note);
    }

    //========================================================================================//
    //Methods
    //========================================================================================//
    //setters
    /**
     * method to set coin grade
     *
     * @param grade the coin grade
     */
    public void setGrade(CoinGrade grade) {
        _grade = grade;
    }

    //getters
    /**
     * method that returns the coin grade
     *
     * @return CoinGrade the coin grade
     */
    public CoinGrade getGrade() {
        return _grade;
    }

    /**
     * method that sets coin face value.
     *
     * @param value the face value of the coin.
     */
    public void setFaceValue(String value) {
        if (value.length() < 20) {
            _faceValue = value;
        } else {
            _faceValue = unknown;
        }
    }
    
    public void setCoinCurrency (CoinCurrency currency)
    {
        _currency = currency;
    }

    /**
     * Method that return the coin face value.
     *
     * @return double the coin face value.
     */
    public String getFaceValue() {
        return _faceValue;
    }
    
    /**
     * method to return coin currency
     * @return coin currency
     */
    public CoinCurrency getCoinCurrency()
    {
        return _currency;
    }

    /**
     * Method that prints coin details
     *
     * @return String with coin details
     */
    @Override
    public String toString() {
        return super.toString() + " grade: " + getGrade();
    }

    public void setCurrency(CoinCurrency currency) {
        _currency = currency;

    }
}
