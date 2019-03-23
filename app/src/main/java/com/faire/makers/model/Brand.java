package com.faire.makers.model;

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

    private String categoriesString;

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
