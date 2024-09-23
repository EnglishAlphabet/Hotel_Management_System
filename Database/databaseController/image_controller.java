/**
* The Code is still unfinished
* May have bugs and unnecessary lines of codes
* Inconsistent practices and codes
* Error handling is yet to be implemented 
*
* */

/* DATABASE TABLE FOR IMAGE 
*
*
*CREATE TABLE image_store (
*id INT auto_increment primary key,
*image_name varchar(255) not null,
*image_data longblob 
*);
*
***********************************/


package beta.database.controller;

import beta.connection.DBConnection;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class image_controller {

    public static boolean store_img(String name, String path){

        String query = "INSERT INTO image_store (image_name, image_data) VALUES(?,?)";
        try(Connection con = DBConnection.getConnection();
        PreparedStatement psmt = con.prepareStatement(query)){

            File image = new File(path);
            FileInputStream fis = new FileInputStream ( image );

            psmt.setString(1,name);
            psmt.setBinaryStream (2, fis, (int) image.length());

            return psmt.executeUpdate()>0;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static List<byte[]> retrieveImage(){
        
        String query = "SELECT * FROM image_store";
        List<byte []> ImgByteList = new ArrayList<>();
            
        try(Connection con = DBConnection.getConnection();
            PreparedStatement psmt = con.prepareStatement(query)){

            ResultSet rs = psmt.executeQuery();
            while(rs.next()){
                String name = rs.getString(2);
                Blob b = rs.getBlob(3);
                byte img[]=b.getBytes(1,(int)b.length());//1 means first image
                ImgByteList.add(img);
                
                //Downloading to desktop
//                FileOutputStream fout=new FileOutputStream("path"+name+".png");
//                fout.write(img);
//                fout.close();
            }
            return ImgByteList;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Blob getImageByID(String id){
        String query = "SELECT * FROM image_store WHERE id = ?";

  

        try(Connection con = DBConnection.getConnection();
            PreparedStatement psmt = con.prepareStatement(query)){

            psmt.setString(1,id);

            ResultSet rs = psmt.executeQuery();
            Blob b=null;
            while(rs.next()){
                 b = rs.getBlob(3);
            }
            return b;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

        retrieveImage();
    }
}
