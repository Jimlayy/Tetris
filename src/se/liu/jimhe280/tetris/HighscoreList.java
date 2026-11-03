package se.liu.jimhe280.tetris;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Collections;

public class HighscoreList
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File HIGHSCORE_FILE = new File("highscore.json");
    private static final File TEMP_FILE = new File("highscore.json.tmp");

    private List<Highscore> highscores = new ArrayList<>();

    public void addScore(Highscore highscore) {
	highscores.add(highscore);
	highscores.sort(new ScoreComparator());
    }

    public List<Highscore> getHighscores() {
	return Collections.unmodifiableList(highscores);
    }

    public void saveHighscoreList() throws IOException {
	String json = GSON.toJson(this);

	// Skriv till temporär
	try (Writer writer = new FileWriter(TEMP_FILE)) {
	    writer.write(json);
	} catch (IOException e) {
	    if (!TEMP_FILE.delete() && TEMP_FILE.exists()) {
		e.addSuppressed(new IOException("Kunde inte ta bort " + TEMP_FILE));
	    }
	    throw e;
	}

	// Ta bort gammal lista
	if (HIGHSCORE_FILE.exists() && !HIGHSCORE_FILE.delete()) {
	    throw new IOException("Kunde inte ta bort " + HIGHSCORE_FILE);
	}
	// Gör temp till nya lista (döpa om)
	if (!TEMP_FILE.renameTo(HIGHSCORE_FILE)) {
	    throw new IOException("Kunde inte döpa om " + TEMP_FILE + " till " + HIGHSCORE_FILE);
	}
    }

    // Betyg 4
    public static HighscoreList loadHighscoreList() throws IOException {
	if (!HIGHSCORE_FILE.exists()) {
	    return new HighscoreList();   // Första körningen, finns ingen fil
	}
	try (Reader reader = new FileReader(HIGHSCORE_FILE)) {
	    HighscoreList list = GSON.fromJson(reader, HighscoreList.class);
	    return list;
	}
    }
}
