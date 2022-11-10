/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    public static int elementCounter = 0;

    void clear() {
        for (int i = 0; i < elementCounter; i++) {
            storage[i] = null;
            elementCounter = 0;
        }

    }

    void save(Resume resume) {
        storage[elementCounter] = resume;
        elementCounter++;
    }

    Resume get(String uuid) {
        Resume target = null;
        for (Resume resume : storage) {
            if (resume.toString().equals(uuid)) {
                target = resume;
                break;
            }
        }
        return target;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i].toString().equals(uuid)) {
                while (storage[i] != null && storage[i + 1] != null) {
                    storage[i] = storage[++i];
                }
                elementCounter--;
                return;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] all = new Resume[elementCounter];
        System.arraycopy(storage, 0, all, 0, elementCounter);
        return all;
    }

    int size() {
        return elementCounter;
    }
}
