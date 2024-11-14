import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Proj3 {
    // Sorting Method declarations
    // Merge Sort
    public static <T extends Comparable> void mergeSort(ArrayList<T> a, int left, int right) {
        // Finish Me
        if (left < right) {
            int middle = (left + right) / 2;

            mergeSort(a, left, middle);
            mergeSort(a, middle + 1, right);

            merge(a, left, middle, right);
        }
    }

    public static <T extends Comparable> void merge(ArrayList<T> a, int left, int mid, int right) {
        // Finish Me
        int n1 = mid - left + 1;
        int n2 = right - mid;

        ArrayList<T> leftA = new ArrayList<>(n1);
        ArrayList<T> rightA = new ArrayList<>(n2);

        for (int i = 0; i < n1; i++) {
            leftA.add(a.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
            rightA.add(a.get(mid + 1 + j));
        }

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (leftA.get(i).compareTo(rightA.get(j)) <= 0) {
                a.set(k, leftA.get(i));
                i++;
            } else {
                a.set(k, rightA.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            a.set(k, leftA.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            a.set(k, rightA.get(j));
            j++;
            k++;
        }
    }


    // Quick Sort
    public static <T extends Comparable> void quickSort(ArrayList<T> a, int left, int right) {
        // Finish Me
        if (left < right) {
            int pivot = partition(a, left, right);

            quickSort(a, left, pivot - 1);
            quickSort(a, pivot + 1, right);
        }
    }

    public static <T extends Comparable> int partition (ArrayList<T> a, int left, int right) {
        // Finish Me
        T pivot = a.get(right);
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (a.get(j).compareTo(pivot) <= 0) {
                i++;
                swap(a, i, j);
            }
        }

        swap(a, i + 1, right);

        return i + 1;
    }

    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }


    // Heap Sort
    public static <T extends Comparable> void heapSort(ArrayList<T> a, int left, int right) {
        // Finish Me
        for (int i = (right + left) / 2; i >= left; i--) {
            heapify(a, i, right);
        }

        for (int i = right; i > left; i--) {
            swap(a, left, i);
            heapify(a, left, i - 1);
        }
    }

    public static <T extends Comparable> void heapify (ArrayList<T> a, int left, int right) {
        // Finish Me
        int largest = left;
        int leftChild = 2 * left + 1;
        int rightChild = 2 * left + 2;

        if (leftChild <= right && a.get(leftChild).compareTo(a.get(largest)) > 0) {
            largest = leftChild;
        }

        if (rightChild <= right && a.get(rightChild).compareTo(a.get(largest)) > 0) {
            largest = rightChild;
        }

        if (largest != left) {
            swap(a, left, largest);
            heapify(a, largest, right);
        }
    }


    // Bubble Sort
    public static <T extends Comparable> int bubbleSort(ArrayList<T> a, int size) {
        // Finish Me
        boolean swapped;
        int swaps = 0;

        for (int i = 0; i < size - 1; i++) {
            swapped = false;
            for (int j = 0; j < size - i - 1; j++) {
                if (a.get(j).compareTo(a.get(j + 1)) > 0) {
                    swap(a, j, j + 1);
                    swapped = true;
                    swaps++;
                }
            }
            if (!swapped) {
                break;
            }
        }
        return swaps;
    }

    // Odd-Even Transposition Sort
    public static <T extends Comparable> int transpositionSort(ArrayList<T> a, int size) {
        // Finish Me
        boolean sorted = false;
        int oddSwaps = 0;
        int evenSwaps = 0;

        while (!sorted) {
            sorted = true;

            for (int i = 1; i < size - 1; i += 2) {
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    sorted = false;
                    oddSwaps++;
                }
            }

            for (int i = 0; i < size - 1; i += 2) {
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    sorted = false;
                    evenSwaps++;
                }
            }
        }
        return Math.max(evenSwaps, oddSwaps);
    }

    public static void main(String [] args)  throws IOException {
        //...
        // Finish Me
        //...
        // Use command line arguments to specify the input file
        if (args.length != 3) {
            System.err.println("Usage: java TestAvl <input file> <sort type> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        String algorithm = args[1];
        int numLines = Integer.parseInt(args[2]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        // FINISH ME

        String[] myMovies = new String[10]; // Array to store movie attributes read from the CSV
        Movies movie;
        String title;
        Integer year = 0;
        String distributor;
        Long budget = 0L;
        Long domOpen = 0L;
        Long domSales = 0L;
        Long intSales = 0L;
        Long wwSales = 0L;
        String runtime;
        String license;

        ArrayList<Movies> data = new ArrayList<>();
        for (int i = 0; i < numLines; i++) {
            String line = inputFileNameScanner.nextLine();
            if (line == null || line.isEmpty()) {
                continue; // Skip empty lines
            } else {
                line = line.trim();
            }
            myMovies = line.split(",");
            if (myMovies.length != 10) {
                continue; // Skip lines that don't have all 10 attributes
            }
            try {
                // Set the movie attributes
                title = myMovies[0] == null ? "N/A" : myMovies[0];
                if (isNumeric(myMovies[1])) {year = Integer.parseInt(myMovies[1] == null ? "0" : myMovies[1]);}
                distributor = myMovies[2] == null ? "N/A" : myMovies[2];
                if (isNumeric(myMovies[3])) {budget = Long.parseLong(myMovies[3] == null ? "0" : myMovies[3]);}
                if (isNumeric(myMovies[4])) {domOpen = Long.parseLong(myMovies[4] == null ? "0" : myMovies[4]);}
                if (isNumeric(myMovies[5])) {domSales = Long.parseLong(myMovies[5] == null ? "0" : myMovies[5]);}
                if (isNumeric(myMovies[6])) {intSales = Long.parseLong(myMovies[6] == null ? "0" : myMovies[6]);}
                if (isNumeric(myMovies[7])) {wwSales = Long.parseLong(myMovies[7] == null ? "0" : myMovies[7]);}
                runtime = myMovies[8] == null ? "0" : myMovies[8];
                license = myMovies[9] == null ? "0" : myMovies[9];

                // Create and set values for the movie object
                movie = new Movies();
                movie.setTitle(title);
                movie.setYear(year);
                movie.setDistributor(distributor);
                movie.setBudget(budget);
                movie.setDomOpen(domOpen);
                movie.setDomSales(domSales);
                movie.setIntSales(intSales);
                movie.setWwSales(wwSales);
                movie.setRuntime(runtime);
                movie.setLicense(license);

                data.add(movie);
            } catch (NumberFormatException e) {
                break;
            }
        }
        inputFileNameScanner.close();

        int swaps;
        double runTime;
        long start, end;
        String content;

        String sortedFile = "./sorted.txt";
        String analysisFile = "./analysis.txt";
        clearFile(sortedFile);

        writeToFile(sortedFile, "Sorting for " + numLines + " movies");
        writeToFile(analysisFile, "Runtimes for " + numLines + " movies");

        switch (algorithm) {
            case "mergeSort":

                // sorted
                Collections.sort(data);
                start = System.nanoTime();
                mergeSort(data, 0, data.size() - 1);
                end = System.nanoTime();
                runTime = end - start;
                runTime = runTime / 1_000_000_000.0;

                content = "Merge Sort (sorted) runtime: " + runTime + " seconds";
                writeToFile(analysisFile, content);
                writeToFile(sortedFile, data.toString());

                // shuffled
                Collections.shuffle(data);
                start = System.nanoTime();
                mergeSort(data, 0, data.size() - 1);
                end = System.nanoTime();
                runTime = end - start;
                runTime = runTime / 1_000_000_000.0;

                content = "Merge Sort (shuffled) runtime: " + runTime + " seconds";
                writeToFile(analysisFile, content);
                writeToFile(sortedFile, data.toString());

                // reversed
                Collections.sort(data, Collections.reverseOrder());
                start = System.nanoTime();
                mergeSort(data, 0, data.size() - 1);
                end = System.nanoTime();
                runTime = end - start;
                runTime = runTime / 1_000_000_000.0;

                content = "Merge Sort (reversed) runtime: " + runTime + " seconds";
                writeToFile(analysisFile, content);
                writeToFile(sortedFile, data.toString());

                break;
            case "quickSort":
                Collections.shuffle(data);
                System.out.println(data);
                quickSort(data, 0, data.size() - 1);
                System.out.println(data);
                break;
            case "heapSort":
                Collections.shuffle(data);
                System.out.println(data);
                heapSort(data, 0, data.size() - 1);
                System.out.println(data);
                break;
            case "bubbleSort":
                Collections.shuffle(data);
                System.out.println(data);
                swaps = bubbleSort(data, data.size());
                System.out.println(data);
                System.out.println("Number of swaps: " + swaps);
            case "transpositionSort":
                Collections.shuffle(data);
                System.out.println(data);
                swaps = transpositionSort(data, data.size());
                System.out.println(data);
                System.out.println("Number of swaps: " + swaps);
                break;
            default:
                content = "Invalid sorting algorithm: " + algorithm;
                writeToFile(content, analysisFile);
                break;
        }
    }

    private static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void writeToFile(String fileName, String content) {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(content + System.lineSeparator());
        } catch (IOException e) {
            System.exit(1);
        }
    }

    private static void clearFile(String fileName) {
        try (FileWriter fw = new FileWriter(fileName, false)) {
            fw.write("");
        } catch (IOException e) {
            System.exit(1);
        }
    }
}
