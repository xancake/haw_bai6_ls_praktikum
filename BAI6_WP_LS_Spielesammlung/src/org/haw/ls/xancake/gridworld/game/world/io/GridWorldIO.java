package org.haw.ls.xancake.gridworld.game.world.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.haw.ls.xancake.gridworld.game.world.GridWorld;
import org.haw.ls.xancake.gridworld.game.world.GridWorldField;
import org.haw.ls.xancake.gridworld.game.world.GridWorldImpl;
import org.haw.ls.xancake.gridworld.game.world.PlayableGridWorld;
import org.haw.ls.xancake.gridworld.game.world.field.DefaultFieldTypes;
import org.haw.ls.xancake.gridworld.game.world.field.FieldType;

public class GridWorldIO {
	private static final String KEYWORD_GRIDWORLD = "gridworld";
	private static final String KEYWORD_START     = "start";
	private static final String KEYWORD_X         = "x";
	private static final String REGEX_WHITESPACE  = "\\s*";
	
	public GridWorldImpl loadGridWorld(String path) throws FileNotFoundException, IOException {
		try {
			File file = path.startsWith("/")
					? new File(path)
					: new File(getClass().getClassLoader().getResource(path).toURI());
			return loadGridWorld(file);
		} catch(URISyntaxException e) {
			throw new IOException(e);
		}
	}
	
	public GridWorldImpl loadGridWorld(File file) throws FileNotFoundException, IOException {
		return loadGridWorld(new FileInputStream(file));
	}
	
	public GridWorldImpl loadGridWorld(InputStream is) throws IOException {
		try(BufferedInputStream input = new BufferedInputStream(is)) {
			Scanner scanner = new Scanner(input);
			GridWorldImpl world = parseGridWorldHeader(scanner);
			parseGridWorldFields(world, scanner);
			parseGridWorldStart(world, scanner);
			return world;
		}
	}
	
	private GridWorldImpl parseGridWorldHeader(Scanner scanner) {
		scanner.skip(KEYWORD_GRIDWORLD).skip(REGEX_WHITESPACE);
		int width = parseNumber(scanner);
		scanner.skip(REGEX_WHITESPACE).skip(KEYWORD_X).skip(REGEX_WHITESPACE);
		int height = parseNumber(scanner);
		scanner.nextLine();
		return new GridWorldImpl(width, height);
	}
	
	private int parseNumber(Scanner scanner) {
		Pattern oldDelimeter = scanner.delimiter();
		scanner.useDelimiter("[^0-9]+");
		int number = scanner.nextInt();
		scanner.useDelimiter(oldDelimeter);
		return number;
	}
	
	private void parseGridWorldFields(GridWorldImpl world, Scanner scanner) {
		for(int y=0; y<world.getHeight(); y++) {
			String line = scanner.next();
			for(int x=0; x<world.getWidth(); x++) {
				char symbol = line.charAt(x);
				FieldType type = DefaultFieldTypes.getForSymbol(symbol);
				world.setFieldType(x, y, type);
			}
			scanner.nextLine();
		}
	}
	
	private void parseGridWorldStart(GridWorldImpl world, Scanner scanner) {
		scanner.skip(KEYWORD_START).skip(REGEX_WHITESPACE);
		int x = parseNumber(scanner);
		scanner.skip(REGEX_WHITESPACE).skip(KEYWORD_X).skip(REGEX_WHITESPACE);
		int y = parseNumber(scanner);
		world.setStartField(x, y);
	}
	
	public void saveGridWorld(GridWorld world, String path) throws IOException {
		try {
			File file = path.startsWith("/")
					? new File(path)
					: new File(getClass().getClassLoader().getResource(path).toURI());
			saveGridWorld(world, file);
		} catch(URISyntaxException e) {
			throw new IOException(e);
		}
	}
	
	public void saveGridWorld(GridWorld world, File file) throws IOException {
		saveGridWorld(world, new FileOutputStream(file));
	}
	
	public void saveGridWorld(GridWorld world, OutputStream os) throws IOException {
		try(PrintWriter writer = new PrintWriter(new BufferedOutputStream(os))) {
			writeGridWorldHeader(world, writer);
			writeGridWorldFields(world, writer);
			writeGridWorldStart((PlayableGridWorld)world, writer);
		}
	}
	
	private void writeGridWorldHeader(GridWorld world, PrintWriter writer) {
		writer.print(KEYWORD_GRIDWORLD);
		writer.print(" ");
		writer.print(world.getWidth());
		writer.print(KEYWORD_X);
		writer.print(world.getHeight());
		writer.println();
	}
	
	private void writeGridWorldFields(GridWorld world, PrintWriter writer) {
		for(int y=0; y<world.getHeight(); y++) {
			for(int x=0; x<world.getWidth(); x++) {
				GridWorldField field = world.getField(x, y);
				writer.print(field.getType());
			}
			writer.println();
		}
	}
	
	private void writeGridWorldStart(GridWorld world, PrintWriter writer) {
		GridWorldField start = world.getStartField();
		writer.print(KEYWORD_START);
		writer.print(" ");
		writer.print(start.getX());
		writer.print(KEYWORD_X);
		writer.print(start.getY());
	}
}
