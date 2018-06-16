package ro.academyplus.swingy.storage;

import ro.academyplus.swingy.model.characters.Hero;
import ro.academyplus.swingy.util.Factory;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HeroStorage {
    private static final String FILE = "heroes.txt";

    private List<Hero> data;

    public HeroStorage() {
        this.data = new ArrayList<>();
        loadFromFile();
    }

    public List<Hero> getAll() {
        return data;
    }

    public Hero getOne(int position) {
        if ((position < 0) || (position >= data.size())) {
            return null;
        }

        return data.get(position);
    }

    public boolean add(@NotNull Hero hero) {
        if (null == hero) {
            return false;
        }

        boolean found = false;
        int index = -1;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getName().equals(hero.getName())) {
                found = true;
                index = i;
            }
        }

        if (!found) {
            data.add(hero);
        } else {
            data.set(index, hero);
        }
        saveToFile();
        return true;
    }

    private void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line = null;

            data = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                Hero hero = Factory.createHero(line);
                data.add(hero);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found");
        } catch (IOException e) {
            System.err.println("IOException in input file");
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            for (int i = 0; i < data.size(); i++) {
                bw.write(data.get(i).toString());
                if (i < data.size() - 1) {
                    bw.newLine();
                }
            }
            bw.flush();
        } catch (IOException e) {
            System.err.println("IOException in output file");
        }
    }
}
