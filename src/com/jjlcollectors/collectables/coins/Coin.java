package com.jjlcollectors.collectables.coins;

import com.jjlcollectors.collectables.CollectableItem;
import java.util.UUID;

/**
 * Class that represents a Coin.
 *
 * @author Nathan Randelman
 * @version 08.09.2014
 */
public final class Coin extends CollectableItem {

    //instance variables

    private String _faceValue;
    private CoinGrade _grade;
    private CoinCurrency _currency;
    private int _coinYear;
    private String _coinMintMark;


    
    private final String UNKNOWN = "UNKNOWN";
    private final int DEFAULT_COIN_YEAR = -1;

    //========================================================================================//
    //Constructors
    //========================================================================================//
    /**
     * Default constructor - sets Unique Coin ID. Name ,facevalue and grade are
     * set to default values.
     * 
     * @param userID the id of the user who the item belongs to.
     * @param name the coin name.
     * @param collectionUUID the collection the item belongs to.
     */
    public Coin(UUID userID, String name,UUID collectionUUID) {
        super(userID, name,collectionUUID);
        _grade = CoinGrade.UNKNOWN;
        _faceValue = UNKNOWN;
        _currency = CoinCurrency.UNKNOWN;
        _coinYear = DEFAULT_COIN_YEAR;
        _coinMintMark = UNKNOWN;
    }

    /**
     * Constructor that sets coin name. Grade set to default.
     *
     * @param userUUID the id of the user who the item belongs to.
     * @param name the coin name.
     * @param grade the Coin grade
     * @param collectionUUID the collection the item belongs to.
     */
    public Coin(UUID userUUID,String name, CoinGrade grade,UUID collectionUUID) {
        super(userUUID,name,collectionUUID);
        setCoinGrade(grade);
    }

    /**
     * Constructor that sets coin name. Grade set to default.
     *@param userUUID the id of the user who the item belongs to.
     * @param name the coin name.
     * @param grade the Coin grade.
     * @param facevalue the coin face value.
     * @param currency the coin currency.
     * @param note the coin note.
     * @param collectionUUID the collection the item belongs to.
     */
    public Coin(UUID userUUID,String name, CoinGrade grade, String facevalue, CoinCurrency currency, StringBuilder note,UUID collectionUUID) {
        this (userUUID,name,grade,collectionUUID);
        setFaceValue(facevalue);
        setCurrency(currency);
        setItemNote(note);
        
    }
    
    /**
     * 
     * @param userUUID the id of the user who the item belongs to.
     * @param name the coin name.
     * @param grade the Coin grade.
     * @param facevalue the coin face value.
     * @param currency the coin currency.
     * @param note the coin note.
     * @param coinYear the coin year
     * @param coinMintMark the coin mint mark
     * @param buyPrice the coin buy price
     * @param coinValue the coin estimated value
     * @param collectionUUID the collection the item belongs to.
     */
    //(userUUID,coinNameTxtField.getText(),coinGradeComboBox.getValue() ,coinFaceValueTxtField.getText(),currencyComboBox.getValue(),coinNote, coinYear,coinMintMarkTxtField.getText(),coinBuyPriceTxtField.getText(),);
    //(UUID userUUID,String name, CoinGrade grade, String facevalue, CoinCurrency currency, StringBuilder note, int coinYear, String coinMintMark, String buyPrice, String coinValue,)
    
    public Coin (UUID userUUID,String name, CoinGrade grade, String facevalue, CoinCurrency currency, StringBuilder note, int coinYear, String coinMintMark, String buyPrice, String coinValue, UUID collectionUUID)
    {
        this (userUUID, name, grade, facevalue, currency, note,collectionUUID);
        setCoinYear(coinYear);
        setCoinMintMark(coinMintMark);
        setItemBuyPrice(buyPrice);
        setFaceValue(coinValue);
        
    }

    /**
     * Copy constructor
     * @param other the other coin to copy from. coin UUID is generated randomly. userID is the same.
     */
    public Coin (Coin other)
    {
        
        this (other.getUserUUID(),other.getItemName(), other.getCoinGrade(), other.getFaceValue(), other.getCoinCurrency(), other.getItemNote(),other.getCoinYear(),other.getCoinMintMark(),other.getBuyPrice(),other.getItemValue(),other.getItemCollectionUUID());
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
    public void setCoinGrade(CoinGrade grade) {
        _grade = grade;
    }

    //getters
    /**
     * method that returns the coin grade
     *
     * @return CoinGrade the coin grade
     */
    public CoinGrade getCoinGrade() {
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
            _faceValue = UNKNOWN;
        }
    }
    
    /**
     * method to get the coin face value.
     * @return coin face value.
     */
    public String getFaceValue()
    {
        return _faceValue;
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
    public String getCoinFaceValue() {
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



    public void setCurrency(CoinCurrency currency) {
        _currency = currency;

    }
    
    /**
     * method to set coin year
     * @param coinYear the year coin was minted (values permitted are 1600 - 2100 including) default value is -1
     */
    public void setCoinYear(int coinYear) 
    {
        if (coinYear >=1600 && coinYear <=2100)
            this._coinYear = coinYear;
        else
            this._coinYear = DEFAULT_COIN_YEAR;
    }
        
    /**
     * method to return coin year
     * @return year coin was minted (between 1600 - 2100) default value is -1
     */
    public int getCoinYear ()
    {
        return this._coinYear;
    }
    
    /**
     * method to set the coin mint mark
     * @param mark the coin mint mark
     */
    public void setCoinMintMark (String mark)
    {
        _coinMintMark = mark;
    }
    
    /**
     * method to return the coin mint mark
     * @return the coin mint mark
     */
    public String getCoinMintMark ()
    {
        return _coinMintMark;
    }
    
    
    /**
     * Method that prints coin details
     *
     * @return String with coin details
     */
    @Override
    public String toString() 
    {
        return super.toString() + " grade: " + getCoinGrade();
    }
}
