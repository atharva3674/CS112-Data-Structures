/*************************************************************************
 *  Compilation:  javac ArtCollage.java
 *  Execution:    java ArtCollage Flo2.jpeg
 *
 *  @author: Atharva Patil
 *
 *************************************************************************/

import java.awt.Color;

public class ArtCollage {

    // The orginal picture
    private Picture original;

    // The collage picture
    private Picture collage;

    // The collage Picture consists of collageDimension X collageDimension tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    private int tileDimension;
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 100
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public ArtCollage (String filename) {

        // WRITE YOUR CODE HERE
        this.collageDimension = 4;
        this.tileDimension = 100;
        this.original = new Picture(filename);
        this.collage = new Picture(this.tileDimension * this.collageDimension, this.tileDimension * this.collageDimension);

        int width = this.collage.width();
        int height = this.collage.height();
        
        for(int col = 0; col < width; col++){
            for(int row = 0; row < height; row++){
                int oC = col * this.original.width() / (collageDimension * tileDimension);
                int oR = row * this.original.height() / (collageDimension * tileDimension);
                Color color = original.get(oC, oR);
                collage.set(col, row, color);
            }
        }

    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public ArtCollage (String filename, int td, int cd) {

        // WRITE YOUR CODE HERE
        this.collageDimension = cd;
        this.tileDimension = td;
        this.original = new Picture(filename);
        
        this.collage = new Picture(this.tileDimension * this.collageDimension, this.tileDimension * this.collageDimension);
        // System.out.println(this.collage.width());
        // System.out.println(this.collage.height());

        int width = this.collage.width();
        int height = this.collage.height();

        for(int col = 0; col < width; col++){
            for(int row = 0; row < height; row++){
                int oC = col * this.original.width() / (collageDimension * tileDimension);
                int oR = row * this.original.height() / (collageDimension * tileDimension);
                Color color = original.get(oC, oR);
                collage.set(col, row, color);
            }
        }
    }

    /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */
    public int getCollageDimension() {

        // WRITE YOUR CODE HERE
        return this.collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */
    public int getTileDimension() {

        // WRITE YOUR CODE HERE
        return this.tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    public Picture getOriginalPicture() {

        // WRITE YOUR CODE HERE
        return this.original; 
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    public Picture getCollagePicture() {

        // WRITE YOUR CODE HERE
        return this.collage;
    }
    
    /*
     * Display the original image
     * Assumes that original has been initialized
     */
    public void showOriginalPicture() {

        // WRITE YOUR CODE HERE
        this.original.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */
    public void showCollagePicture() {

        // WRITE YOUR CODE HERE
        this.collage.show();
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) {

        // WRITE YOUR CODE HERE

        ArtCollage replace = new ArtCollage(filename, this.tileDimension, this.collageDimension);
        replace.makeCollage();

        // Picture replace = new Picture(filename);

       /* for(int col = 0; col < replace.tileDimension; col++){
            for(int row = 0; row < replace.tileDimension; row++){
                int oC = col * replace.original.width() / (replace.tileDimension);
                int oR = row * replace.original.height() / (replace.tileDimension);
                Color color = replace.original.get(oC, oR);
                replace.collage.set(col, row, color);
            }
        }

        replace.collage.show(); */
        
        
        for(int col = collageCol * this.tileDimension; col < collageCol * this.tileDimension + this.tileDimension; col++){
            for(int row = collageRow * this.tileDimension; row < collageRow * this.tileDimension + this.tileDimension; row++){
                Color color = replace.collage.get(col, row);
                this.collage.set(col, row, color);
            }
        }
    }
    
    /*
     * Makes a collage of tiles from original Picture
     * original will have collageDimension x collageDimension tiles, each tile
     * has tileDimension X tileDimension pixels
     */
    public void makeCollage () {

        // WRITE YOUR CODE HERE

        for(int tileC = 0; tileC < this.collageDimension; tileC++){
            for(int tileR = 0; tileR < this.collageDimension; tileR++){
                for(int col = 0; col < this.tileDimension; col++){
                    for(int row = 0; row < this.tileDimension; row++){
                        int oC = col * this.original.width() / (tileDimension);
                        int oR = row * this.original.height() / (tileDimension);
                        Color color = original.get(oC, oR);
                        collage.set(col + tileC * this.tileDimension, row + tileR * this.tileDimension, color);
                    }
                }
            }
        }
        
        
    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component 
     * (see CS111 Week 9 slides, the code for color separation is at the 
     *  book's website)
     *
     * @param component is either red, blue or green
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) {

        // WRITE YOUR CODE HERE
        

        for(int col = collageCol * this.tileDimension; col < collageCol * this.tileDimension + this.tileDimension; col++){
            for(int row = collageRow * this.tileDimension; row < collageRow * this.tileDimension + this.tileDimension; row++){
                Color color = collage.get(col, row);
                int red = color.getRed();
                int blue = color.getBlue();
                int green = color.getGreen();

                if(component.equals("red")){
                    collage.set(col, row, new Color(red, 0, 0));
                }
                else if(component.equals("blue")){
                    collage.set(col, row, new Color(0, 0, blue));
                }
                else if(component.equals("green")){
                    collage.set(col, row, new Color(0, green, 0));
                }
                
            }
        }
        
    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     * (see CS111 Week 9 slides, the code for luminance is at the book's website)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */

    public void grayscaleTile (int collageCol, int collageRow) {

        // WRITE YOUR CODE HERE
        
        for(int col = collageCol * this.tileDimension; col < collageCol * this.tileDimension + this.tileDimension; col++){
            for(int row = collageRow * this.tileDimension; row < collageRow * this.tileDimension + this.tileDimension; row++){
                Color color = this.collage.get(col, row);
                Color gray = Luminance.toGray(color);
                this.collage.set(col, row, gray);
            }
        }
    }


    /*
     *
     *  Test client: use the examples given on the assignment description to test your ArtCollage
     */
    public static void main (String[] args) {

       // ArtCollage art = new ArtCollage(args[0], 200, 3); 
        ArtCollage art = new ArtCollage(args[0], 200, 2);
        
        art.makeCollage();
        /*
        art.grayscaleTile(1, 0);
        art.replaceTile("Flo2.jpeg", 1, 1);
        art.colorizeTile("red", 1, 2);
        art.showCollagePicture(); */
        // art.showOriginalPicture();

        art.replaceTile("PlocLilo.jpg", 0, 1);
        art.replaceTile("Flo2.jpeg", 1, 0);
        art.replaceTile("Baloo.jpeg", 1, 1);
        art.colorizeTile("green", 0, 0);
        
        art.showCollagePicture();



    }
}
