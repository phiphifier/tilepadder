import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.File;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public class TilePadder {

	public static void main(String[] args) {

		// CHECK FOR HELP FLAGS OR NO ARGS
		if (args.length == 0 || args[0].equals("--help") || args[0].equals("-help") || args[0].equals("--h") || args[0].equals("-h")) {
			System.out.println("\nUse a positive number as padding amount to add padding to a tileset without any, or use a negative number for padding amount to remove existing padding.\n\nOutput files can found in the same directory as the source file.\n\nUsage if installer was used on linux:\n  tilepadder [PATH TO IMAGE] [TILE SIZE] [PADDING AMOUNT]\n\nUsage from java file directly:\n  java [PATH TO TILE PADDER JAVA FILE] [PATH TO IMAGE] [TILE SIZE] [PADDING AMOUNT]\n");
			return;
		}
		
		// MAKE SURE THERE'S ENOUGH ARGS
		if (args.length > 3) {
			System.out.println("Too many arguments! Use --help or -h to view usage");
			return;
		}
		
		// CORRECT AMOUNT OF ARGS PRESENT, INITIALIZE NECESSARY FIELDS
		File tilesetFile = null;
		BufferedImage tileset = null;
		int tileSize = 0;
		int paddingAmount = 0;
		
		// LOAD TILESET
		try {
			tilesetFile = new File(args[0]);
			tileset = ImageIO.read(tilesetFile);
		}
		catch(Exception e) {
			System.out.println("Image not found at this location!");
			return;
		}
		
		// CHECK TILE SIZE
		try {
			tileSize = Integer.parseInt(args[1]);
			if (tileSize == 0) {
				System.out.println("Tile size can't be zero!");
				return;
			}
		}
		catch(Exception e) {
			System.out.println("Tile size must be a valid integer!");
			return;

		}
		
		// CHECK PADDING AMOUNT

		try {
			paddingAmount = Integer.parseInt(args[2]);
            if (paddingAmount == 0) {
                System.out.println("Padding amount can't be zero!");
                return;
            }
		}

		catch(Exception e) {
			System.out.println("Padding amount must be a valid integer!");
			return;
		}
		
		// MAKE SURE SIZES MAKE SENSE
		if (tileset.getWidth() < tileSize || tileset.getHeight() < tileSize) {
			System.out.println("Tile size can't be larger than the source image!");
			return;
		}
		
		BufferedImage output = null;
		String outputLocation = tilesetFile.getParent();
		if (outputLocation == null) outputLocation = ".";
		if (paddingAmount > 0) output = addPadding(tileset, tileSize, paddingAmount, true);
		if (paddingAmount < 0) output = removePadding(tileset, tileSize, paddingAmount, true);
		writeOutputToDisk(output, outputLocation);
		
		// FINISHED
		System.out.println("Finished!\n\n**This process can be destructive if misconfigured, check the result first if you intend on deleting the source tileset!**\n");
	}
	
	
	
	public static BufferedImage addPadding(BufferedImage tileset, int tileSize, int paddingAmount, boolean printProgress) {
		BufferedImage output = null;
		if (paddingAmount >= 0) {
			int widthInTiles = tileset.getWidth()/tileSize;
			int heightInTiles = tileset.getHeight()/tileSize;
			int newImageWidth = tileset.getWidth()+paddingAmount*2*widthInTiles;
			int newImageHeight = tileset.getHeight()+paddingAmount*2*heightInTiles;
			output = new BufferedImage(newImageWidth, newImageHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = (Graphics2D)output.getGraphics();
			int totalTilesToBePadded = widthInTiles*heightInTiles;
			int tilesPadded = 0;
			int lastPercent = 0;
			for (int y = 0; y < heightInTiles; y++) {
				for (int x = 0; x < widthInTiles; x++) {
					BufferedImage currentTile = tileset.getSubimage(x*tileSize, y*tileSize, tileSize, tileSize);
					int tileStartX = x*tileSize+paddingAmount*x*2+paddingAmount;
					int tileStartY = y*tileSize+paddingAmount*y*2+paddingAmount;
					g2.drawImage(currentTile, tileStartX, tileStartY, tileSize, tileSize, null);
					g2.drawImage(currentTile.getSubimage(0, 0, tileSize, 1), tileStartX, tileStartY-paddingAmount, tileSize, paddingAmount, null);
					g2.drawImage(currentTile.getSubimage(0, tileSize-1, tileSize, 1), tileStartX, tileStartY+tileSize, tileSize, paddingAmount, null);
					g2.drawImage(currentTile.getSubimage(0, 0, 1, tileSize), tileStartX-paddingAmount, tileStartY, paddingAmount, tileSize, null);
					g2.drawImage(currentTile.getSubimage(tileSize-1, 0, 1, tileSize), tileStartX+tileSize, tileStartY, paddingAmount, tileSize, null);
					g2.drawImage(currentTile.getSubimage(0, 0, 1, 1), tileStartX-paddingAmount, tileStartY-paddingAmount, paddingAmount, paddingAmount, null);
					g2.drawImage(currentTile.getSubimage(0, tileSize-1, 1, 1), tileStartX-paddingAmount, tileStartY+tileSize, paddingAmount, paddingAmount, null);
					g2.drawImage(currentTile.getSubimage(tileSize-1, 0, 1, 1), tileStartX+tileSize, tileStartY-paddingAmount, paddingAmount, paddingAmount, null);
					g2.drawImage(currentTile.getSubimage(tileSize-1, tileSize-1, 1, 1), tileStartX+tileSize, tileStartY+tileSize, paddingAmount, paddingAmount, null);
					tilesPadded++;
					int completionPercent = (int)((float)tilesPadded/(float)totalTilesToBePadded*100f);
					if (lastPercent != completionPercent) {
						if (printProgress) System.out.println(completionPercent+"% complete");
						lastPercent = completionPercent;
					}
				}
			}
			g2.dispose();
		}
		return output;
	}
	
	
	
	public static BufferedImage removePadding(BufferedImage tileset, int tileSize, int paddingAmount, boolean printProgress) {
		BufferedImage output = null;
		if (paddingAmount < 0) {
			int widthInTiles = tileset.getWidth()/(tileSize+Math.abs(paddingAmount)*2);
			int heightInTiles = tileset.getHeight()/(tileSize+Math.abs(paddingAmount)*2);
			int newImageWidth = widthInTiles*tileSize;
			int newImageHeight = heightInTiles*tileSize;
			output = new BufferedImage(newImageWidth, newImageHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = (Graphics2D)output.getGraphics();
			int totalTilesToBeUnpadded = widthInTiles*heightInTiles;
			int tilesUnpadded = 0;
			int lastPercent = 0;
			for (int y = 0; y < heightInTiles; y++) {
				for (int x = 0; x < widthInTiles; x++) {
					BufferedImage currentTile = tileset.getSubimage(x*tileSize+Math.abs(paddingAmount)*2*x+Math.abs(paddingAmount), y*tileSize+Math.abs(paddingAmount)*2*y+Math.abs(paddingAmount), tileSize, tileSize);
					int tileStartX = x*tileSize;
					int tileStartY = y*tileSize;
					g2.drawImage(currentTile, tileStartX, tileStartY, tileSize, tileSize, null);
					tilesUnpadded++;
					int completionPercent = (int)((float)tilesUnpadded/(float)totalTilesToBeUnpadded*100f);
					if (lastPercent != completionPercent) {
						if (printProgress) System.out.println(completionPercent+"% complete");
						lastPercent = completionPercent;
					}
				}
			}
			g2.dispose();
		}
		return output;
	}
	
	
	
	private static void writeOutputToDisk(BufferedImage output, String outputLocation) {
		try {
			final String outputDefaultName = "processed tileset";
			String outputName = outputDefaultName+".png";
			int sameNameFiles = 0;
			while (new File(Path.of(outputLocation,outputName).toString()).exists()) {
				sameNameFiles++;
				outputName = outputDefaultName+"("+sameNameFiles+")"+".png";
			}
			ImageIO.write(output, "png", new File(Path.of(outputLocation,outputName).toString()));
		}
		catch(Exception e) {
			System.out.println("Failed to write file to disk!");
		}
	}
}
