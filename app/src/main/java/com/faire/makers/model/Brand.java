package com.faire.makers.model;

/**
 * POJO for Brand.
 */
public class Brand {

    public boolean active;
    public String token;
    public String token_alias;
    public String name;
    public String description;
    public String short_description;
    public String url;
    public String[] categories;
    public Image[] images;
    public String made_in;
    public String made_in_state;
    public String made_in_city;
    public long latidude;
    public long longitude;

    private String categoriesString;

    /**
     * Gets all categories listed for the brand.
     *
     * @return A single line with all brands listed separated by comma.
     */
    public String getCategories(){
        if(categoriesString != null){
            return categoriesString;
        }

        if(categories == null || categories.length == 0){
            categoriesString = "";
        } else {
            StringBuffer sb = new StringBuffer();

            for(String s : categories){
                sb.append(s);
                sb.append(", ");
            }

            String temp = sb.toString();
            categoriesString = temp.substring(0, temp.length() - 2);
        }


        return categoriesString;
    }
}
