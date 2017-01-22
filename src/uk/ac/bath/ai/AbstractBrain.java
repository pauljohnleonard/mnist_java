package uk.ac.bath.ai;

import uk.ac.bath.ai.util.Result;
import uk.ac.bath.ai.util.Util;

import uk.ac.bath.ai.image.DisplayImage;
import uk.ac.bath.ai.image.FloatVectorImage;

import java.awt.Dimension;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Observable;
import java.util.Random;
import java.util.Vector;
import uk.ac.bath.util.Tweakable;

/**
 * Base class for Brains
 *
 * @author pjl
 */
public abstract class AbstractBrain implements Brain {

    private static final long serialVersionUID = 0L;

    transient protected Vector<DisplayImage> imagination;
    protected Vector<Tweakable> tweaks;
  //  protected Result result;
    protected Dimension dPixel;
    protected Dimension dLabel;
    protected int nPixel;
    protected int nLabel;
    protected Random rand;
    private String name;

    protected AbstractBrain() {
        rand = Util.createRandom();
    }

    ;

    protected AbstractBrain(Dimension dIn, Dimension dOut, String name) {
        this();
        init(dIn, dOut, name);
    }

    protected void init(Dimension dIn, Dimension dOut, String name) {
        this.dPixel = dIn;
        this.dLabel = dOut;
        this.name = name;
        init();
    }

    void init() {
        nPixel = dPixel.width * dPixel.height;
        nLabel = dLabel.width * dLabel.height;
    //    result = new Result(dPixel, dLabel);
        tweaks = new Vector<Tweakable>();
        imagination = new Vector<DisplayImage>();
    }

    public int getImaginationCount() {
        return imagination.size();
    }

    public DisplayImage getImagination(int i) {
        return imagination.get(i);
    }

    public Vector<Tweakable> getTweaks() {
        return tweaks;
    }

    public String getName() {
        return name;
    }
    
    
    protected void addImage(DisplayImage image) {
        imagination.add(image);
    }

    public Random getRandom() {
        return rand;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(dPixel);
        out.writeObject(dLabel);
        out.writeObject(name);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        dPixel = (Dimension) in.readObject();
        dLabel = (Dimension) in.readObject();
        name = (String) in.readObject();
        init();
    }
}
