import java.util.Arrays;
import java.util.Random;

// Абстрактный класс для сортировки
abstract class Sorter {
    public abstract void sort(int[] array);

    protected void checkArray(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Пустой массив");
        }
    }
}

// Класс для пузырьковой сортировки
class BubbleSorter extends Sorter {
    @Override
    public void sort(int[] array) {
        checkArray(array);
        int n = array.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (array[i - 1] > array[i]) {
                    int temp = array[i - 1];
                    array[i - 1] = array[i];
                    array[i] = temp;
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
    }
}

// Класс для сортировки вставками
class InsertionSorter extends Sorter {
    @Override
    public void sort(int[] array) {
        checkArray(array);
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }
}

public class Main {

    public static long measureSortTime(Sorter sorter, int[] array) {
        sorter.checkArray(array);
        long startTime = System.nanoTime();
        sorter.sort(array);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static long sortAndMeasureTime(int[] originalArray, Sorter sorter) {
        int[] arrayCopy = Arrays.copyOf(originalArray, originalArray.length);
        return measureSortTime(sorter, arrayCopy);
    }

    public static void main(String[] args) {
        // Создаем массив случайных чисел
        int[] array = new int[10000];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(10000);
        }

        Sorter bubbleSorter = new BubbleSorter();
        Sorter insertionSorter = new InsertionSorter();

        long bubbleSortTime = sortAndMeasureTime(array, bubbleSorter);
        long insertionSortTime = sortAndMeasureTime(array, insertionSorter);
        long arraysSortTime = sortAndMeasureTime(array, new Sorter() {
            @Override
            public void sort(int[] array) {
                Arrays.sort(array);
            }
        });

        // Перевод времени из наносекунд в секунды
        double bubbleSortTimeSeconds = bubbleSortTime / 1e9;
        double insertionSortTimeSeconds = insertionSortTime / 1e9;
        double arraysSortTimeSeconds = arraysSortTime / 1e9;

        // Вывод результатов
        System.out.println("Время выполнения пузырьковой сортировки: " + bubbleSortTimeSeconds + " секунд");
        System.out.println("Время выполнения сортировки вставками: " + insertionSortTimeSeconds + " секунд");
        System.out.println("Время выполнения Arrays.sort(): " + arraysSortTimeSeconds + " секунд");

        // Определение самого быстрого алгоритма
        if (bubbleSortTimeSeconds < insertionSortTimeSeconds && bubbleSortTimeSeconds < arraysSortTimeSeconds) {
            System.out.println("Пузырьковая сортировка была самой быстрой.");
        } else if (insertionSortTimeSeconds < bubbleSortTimeSeconds && insertionSortTimeSeconds < arraysSortTimeSeconds) {
            System.out.println("Сортировка вставками была самой быстрой.");
        } else {
            System.out.println("Стандартная сортировка Arrays.sort() была самой быстрой.");
        }
    }
}
