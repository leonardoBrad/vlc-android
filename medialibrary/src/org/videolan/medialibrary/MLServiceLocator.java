package org.videolan.medialibrary;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;

import org.videolan.libvlc.interfaces.IMedia;
import org.videolan.medialibrary.interfaces.Medialibrary;
import org.videolan.medialibrary.interfaces.media.AbstractAlbum;
import org.videolan.medialibrary.interfaces.media.AbstractArtist;
import org.videolan.medialibrary.interfaces.media.AbstractFolder;
import org.videolan.medialibrary.interfaces.media.AbstractGenre;
import org.videolan.medialibrary.interfaces.media.MediaWrapper;
import org.videolan.medialibrary.interfaces.media.AbstractPlaylist;
import org.videolan.medialibrary.interfaces.media.AbstractVideoGroup;
import org.videolan.medialibrary.media.Album;
import org.videolan.medialibrary.media.Artist;
import org.videolan.medialibrary.media.Folder;
import org.videolan.medialibrary.media.Genre;
import org.videolan.medialibrary.media.MediaWrapperImpl;
import org.videolan.medialibrary.media.Playlist;
import org.videolan.medialibrary.media.VideoGroup;
import org.videolan.medialibrary.stubs.StubAlbum;
import org.videolan.medialibrary.stubs.StubArtist;
import org.videolan.medialibrary.stubs.StubFolder;
import org.videolan.medialibrary.stubs.StubGenre;
import org.videolan.medialibrary.stubs.StubMediaWrapper;
import org.videolan.medialibrary.stubs.StubMedialibrary;
import org.videolan.medialibrary.stubs.StubPlaylist;
import org.videolan.medialibrary.stubs.StubVideoGroup;

public class MLServiceLocator {

    private static LocatorMode sMode = LocatorMode.VLC_ANDROID;
    private static volatile Medialibrary instance;

    public static void setLocatorMode(LocatorMode mode) {
        if (instance != null && mode != sMode) {
            throw new IllegalStateException("LocatorMode must be set before Medialibrary initialization");
        }
        MLServiceLocator.sMode = mode;
    }
    public static LocatorMode getLocatorMode() { return MLServiceLocator.sMode; }

    public static String EXTRA_TEST_STUBS = "extra_test_stubs";
    public enum LocatorMode {
        VLC_ANDROID,
        TESTS,
    }

    public static synchronized Medialibrary getAbstractMedialibrary() {
        if (instance == null) {
            instance = sMode == LocatorMode.VLC_ANDROID ? new MedialibraryImpl() : new StubMedialibrary();
        }
        return instance;
    }

    // MediaWrapper
    public static MediaWrapper getAbstractMediaWrapper(long id, String mrl, long time, long length,
                                                       int type, String title, String filename,
                                                       String artist, String genre, String album,
                                                       String albumArtist, int width, int height,
                                                       String artworkURL, int audio, int spu,
                                                       int trackNumber, int discNumber, long lastModified,
                                                       long seen, boolean isThumbnailGenerated, int releaseDate) {
        if (sMode == LocatorMode.VLC_ANDROID) {
            return new MediaWrapperImpl(id, mrl, time, length, type, title,
                    filename, artist, genre, album, albumArtist, width, height, artworkURL,
                    audio, spu, trackNumber, discNumber, lastModified, seen, isThumbnailGenerated, releaseDate);
        } else {
            return new StubMediaWrapper(id, mrl, time, length, type, title,
                    filename, artist, genre, album, albumArtist, width, height, artworkURL,
                    audio, spu, trackNumber, discNumber, lastModified, seen, isThumbnailGenerated, releaseDate);
        }
    }

    public static MediaWrapper getAbstractMediaWrapper(Uri uri, long time, long length, int type,
                                                       Bitmap picture, String title, String artist,
                                                       String genre, String album, String albumArtist,
                                                       int width, int height, String artworkURL,
                                                       int audio, int spu, int trackNumber,
                                                       int discNumber, long lastModified, long seen) {
        if (sMode == LocatorMode.VLC_ANDROID) {
            return new MediaWrapperImpl(uri, time, length, type, picture, title, artist, genre,
                    album, albumArtist, width, height, artworkURL, audio, spu, trackNumber,
                    discNumber, lastModified, seen);
        } else {
            return new StubMediaWrapper(uri, time, length, type, picture, title, artist, genre,
                    album, albumArtist, width, height, artworkURL, audio, spu, trackNumber,
                    discNumber, lastModified, seen);
        }
    }

    public static MediaWrapper getAbstractMediaWrapper(Uri uri) {
        if (sMode == LocatorMode.VLC_ANDROID) {
            return new MediaWrapperImpl(uri);
        } else {
            return new StubMediaWrapper(uri);
        }
    }

    public static MediaWrapper getAbstractMediaWrapper(IMedia media) {
        if (sMode == LocatorMode.VLC_ANDROID) {
            return new MediaWrapperImpl(media);
        } else {
            return new StubMediaWrapper(media);
        }
    }

    public static MediaWrapper getAbstractMediaWrapper(Parcel in) {
        if (sMode == LocatorMode.VLC_ANDROID) {
            return new MediaWrapperImpl(in);
        } else {
            return new StubMediaWrapper(in);
        }
    }

    //Artist
    public static AbstractArtist getAbstractArtist(long id, String name, String shortBio, String artworkMrl, String musicBrainzId) {
        if (sMode == LocatorMode.VLC_ANDROID) {
            return new Artist(id, name, shortBio, artworkMrl, musicBrainzId);
        } else {
            return new StubArtist(id, name, shortBio, artworkMrl, musicBrainzId);
        }
    }

    public static AbstractArtist getAbstractArtist(Parcel in) {
        if (sMode == LocatorMode.VLC_ANDROID) {
            return new Artist(in);
        } else {
            return new StubArtist(in);
        }
    }

    //Genre
    public static AbstractGenre getAbstractGenre(long id, String title) {
        if (sMode == LocatorMode.VLC_ANDROID) {
            return new Genre(id, title);
        } else {
            return new StubGenre(id, title);
        }
    }

    public static AbstractGenre getAbstractGenre(Parcel in) {
        if (sMode == LocatorMode.VLC_ANDROID) {
            return new Genre(in);
        } else {
            return new StubGenre(in);
        }
    }

    //Album
    public static AbstractAlbum getAbstractAlbum(long id, String title, int releaseYear, String artworkMrl,
                                                 String albumArtist, long albumArtistId, int nbTracks,
                                                 long duration) {
        if (sMode == LocatorMode.VLC_ANDROID) {
            return new Album(id, title, releaseYear, artworkMrl, albumArtist, albumArtistId,
                    nbTracks, duration);
        } else {
            return new StubAlbum(id, title, releaseYear, artworkMrl, albumArtist, albumArtistId,
                    nbTracks, duration);
        }
    }

    public static AbstractAlbum getAbstractAlbum(Parcel in) {
        if (sMode == LocatorMode.VLC_ANDROID) {
            return new Album(in);
        } else {
            return new StubAlbum(in);
        }
    }

    //Folder
    public static AbstractFolder getAbstractFolder(long id, String name, String mrl) {
        if (sMode == LocatorMode.VLC_ANDROID) {
            return new Folder(id, name, mrl);
        } else {
            return new StubFolder(id, name, mrl);
        }
    }

    public static AbstractFolder getAbstractFolder(Parcel in) {
        if (sMode == LocatorMode.VLC_ANDROID) {
            return new Folder(in);
        } else {
            return new StubFolder(in);
        }
    }

    public static AbstractVideoGroup getAbstractVideoGroup(Parcel in) {
        if (sMode == LocatorMode.VLC_ANDROID) {
            return new VideoGroup(in);
        } else {
            return new StubVideoGroup(in);
        }
    }

    //Playlist
    public static AbstractPlaylist getAbstractPlaylist(long id, String name, int trackCount) {
        if (sMode == LocatorMode.VLC_ANDROID) {
            return new Playlist(id, name, trackCount);
        } else {
            return new StubPlaylist(id, name, trackCount);
        }
    }

    public static AbstractPlaylist getAbstractPlaylist(Parcel in) {
        if (sMode == LocatorMode.VLC_ANDROID) {
            return new Playlist(in);
        } else {
            return new StubPlaylist(in);
        }
    }
}
