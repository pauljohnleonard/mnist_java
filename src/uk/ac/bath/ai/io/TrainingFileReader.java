/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author pjl
 */
public class TrainingFileReader {

    RandomAccessFile fis;
    private DATA key;
    private int ndim;
    private int dim[];
    private long zero;
    private long size;


    public int [] getSizes() {
        return dim;
    }


    enum DATA {

        U8(0x08,1), S89(0x09,1), S16(0x0B,2), U16(0x0C,2), FLOAT(0x0D,4), DOUBLE(0x0E,8);
        private byte TYPE;
        private int size;
      //  private Class class;
        DATA(int t,int size) {
            TYPE = (byte) t;
            this.size=size;
        }
    };

    public TrainingFileReader(File file) throws FileNotFoundException, IOException {
        byte buff[] = new byte[4];
        fis = new RandomAccessFile(file, "r");
        fis.readFully(buff, 0, 4);
       
        byte akey = buff[2];

        for (DATA d : DATA.values()) {
            if (d.TYPE == akey) {
                key = d;
                break;
            }
        }

        System.out.println(" Type = " + key);


        ndim = buff[3];


        System.out.println(" Ndim = " + ndim);


        dim = new int[ndim];

        System.out.print(" Dims = [");
        for (int i = 0; i < ndim; i++) {
            dim[i] = fis.readInt();
            if (i > 0) {
                System.out.print(",");
            }
            System.out.print(dim[i]);
        }
        System.out.println("]");
        
        zero=fis.getFilePointer();
        size=key.size;
        
        
        for (int i=1;i<ndim;i++) {
            size=dim[i]*size;
        }
        
    }

    
    
    public Object getData(int index) throws Exception {
        
        Object ret=null;
        
        if (key.size == 1) {   
            fis.seek(zero+size*index);
            byte [][] o=new byte[dim[1]][dim[2]];    
            for (int i=0;i<dim[1];i++) {
                fis.readFully(o[i],0,dim[2]);
            }    
            ret=o;
        }
        
        return ret;
    }

    
    public int getLabel(int index) throws Exception {
        if (key.size == 1) {   
            fis.seek(zero+size*index);
            return fis.readByte();
   
        }
        
        throw new Exception(" Data is "+ key);
        
        
    }
    
    public static void main(String args[]) throws FileNotFoundException, IOException {
        File file = new File("/home/pjl/lect/IS/notes/NeuralNets/GeoffHinton/t10k-images-idx3-ubyte");
        new TrainingFileReader(file);
    }
}
