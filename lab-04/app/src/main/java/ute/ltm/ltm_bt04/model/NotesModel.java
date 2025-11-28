package ute.ltm.ltm_bt04.model;

public class NotesModel {
    private int IdNotes;
    private String NameNotes;

    public NotesModel(int idNotes, String nameNotes) {
        IdNotes = idNotes;
        NameNotes = nameNotes;
    }

    public int getIdNotes() {
        return IdNotes;
    }

    public void setIdNotes(int idNotes) {
        IdNotes = idNotes;
    }

    public String getNameNotes() {
        return NameNotes;
    }

    public void setNameNotes(String nameNotes) {
        NameNotes = nameNotes;
    }
}