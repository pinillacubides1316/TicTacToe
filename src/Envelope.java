
import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author adria
 */
//POJO - Plain old java Object. Passed JsonObjects from one page to other - it break the data into binary state. 
public class Envelope implements Serializable{
    //what command is this
    private String id;
    
    // are there any specifics that need to be stablished
    private String args;
    
    //the main data of the command
    private Object contents;
    
    //wathever we need to pull, needs to have a getter and a setter and a blanc constructor
    public Envelope() {
    }

    public Envelope(String id, String args, Object contents) {
        this.id = id;
        this.args = args;
        this.contents = contents;
    }

    public String getId() {
        return id;
    }

    public String getArgs() {
        return args;
    }

    public Object getContents() {
        return contents;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public void setContents(Object contents) {
        this.contents = contents;
    }
    
    
    
}
