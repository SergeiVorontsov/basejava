import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    public int countResumes = 0;

    void clear() {
        for (int i = 0; i < countResumes; i++) {
            storage[i] = null;
        }
        countResumes = 0;
    }

    void save(Resume resume) {
        storage[countResumes] = resume;
        countResumes++;
    }

    Resume get(String uuid) {
        int i;
        for (i = 0; i < countResumes; i++) {
            if (storage[i].toString().equals(uuid)) {
                break;
            }
        }
        return storage[i];
    }

    void delete(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].toString().equals(uuid)) {
                while (i < countResumes) {
                    storage[i] = storage[++i];
                }
            }
        }
        countResumes--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    int size() {
        return countResumes;
    }
}
