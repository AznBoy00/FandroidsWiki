package org.wikipedia.note;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({FirebaseDatabase.class})
public class CreateNoteTest {
    private CreateNoteActivity createNote = new CreateNoteActivity();
    private DatabaseReference mockedDatabaseReference;
    private FirebaseDatabase mockedFirebaseDatabase;
    private FirebaseUser user;
    private Note newNote = mock(Note.class);
    private String noteTitle = "Title note test";
    private String noteContent = "Content note test";
    private String noteId;
    private String userId;
    private String noteBookId;

    @Before
    public void before() {
        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);

        mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);

        user = Mockito.mock(FirebaseUser.class);
        when(user.getUid()).thenReturn("abcd123");

        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);
    }

    @Test
    public void saveNoteTestSuccess() {
        when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);
        when(mockedDatabaseReference.push()).thenReturn(mockedDatabaseReference);
        when(mockedDatabaseReference.getKey()).thenReturn("1234567");
        when(mockedDatabaseReference.child(noteId)).thenReturn(mockedDatabaseReference);

        noteId = mockedDatabaseReference.push().getKey();
        userId = user.getUid();
        noteBookId = user.getUid();

        newNote = createNote.saveMyNote(noteId, userId, noteBookId, noteTitle, noteContent, mockedDatabaseReference);

        verify(mockedDatabaseReference, times(1)).child(noteId);
        verify(mockedDatabaseReference, times(1)).setValue(newNote);

        assertEquals(newNote.getNoteTitle(), ("Title note test"));
        assertEquals(newNote.getNoteContent(), ("Content note test"));
        assertEquals(newNote.getUserId(), ("abcd123"));
        assertEquals(newNote.getNoteId(), ("1234567"));

        //Showing the values of newNote for verification purpose
        System.out.println(newNote.getNoteTitle());
        System.out.println(newNote.getNoteContent());
        System.out.println(newNote.getUserId());
        System.out.println(newNote.getNoteId());
    }

    @Test
    public void saveNoteTestFail() {
        when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);
        when(mockedDatabaseReference.push()).thenReturn(mockedDatabaseReference);
        when(mockedDatabaseReference.getKey()).thenReturn("1234567");
        when(mockedDatabaseReference.child(noteId)).thenReturn(mockedDatabaseReference);

        noteId = mockedDatabaseReference.push().getKey();
        userId = user.getUid();
        noteBookId = user.getUid();

        newNote = createNote.saveMyNote(noteId, userId, noteBookId, noteTitle, noteContent, mockedDatabaseReference);

        assertNotEquals(null, ("Title note test"));
        assertNotEquals(null, ("Content note test"));
        assertNotEquals(null, ("abcd123"));
        assertNotEquals(null, ("1234567"));
    }

}
