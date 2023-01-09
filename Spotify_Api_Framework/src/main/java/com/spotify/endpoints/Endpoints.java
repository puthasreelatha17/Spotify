package com.spotify.endpoints;

public class Endpoints {
	public static int status=200;
	public static int time=4000;
	/***********ALBUMS ENDPOINTS**********/

	public static String GET_A_ALBUM="/albums/";
	public static String GETALBUMS="/albums/";
	public static String GETALBUMTRACKS="/albums/{id}/tracks";
	public static String GETUSERSAVEDALBUMS="/me/albums";
	public static String CHECKUSERSAVEDALBUMS="/me/albums/contains";
	public static String GETNEWRELEASES="/browse/new-releases";
	public static String GETSEVERALALBUMS="/albums";
	
	/****************MARKETS ENDPOINTS******************/
	public static String GETAVAILABEMARKETS="/markets";
	
	/******************SEARCH ENDPOINTS*******************/
	public static String GETSEARCH="/search?q=albums&type=album";
	
	/*************GENRES*************/
	public static String GETGENRES="/recommendations/available-genre-seeds";
	
	/*************ARTISTS**************/
	public static String GETARTIST="/artists/";
	public static String GETSEVERSLARTIST="/artists/";
	public static String GETARTISTALBUMS="/artists/{id}/albums";
	public static String GETARTISTTOPTRACKS="/artists/{id}/top-tracks?market=IN";
	public static String GETARTISTRELATEDARTISTS="/artists/{id}/related-artists";
	
	/*************CATEGORIES**************/
	public static String SINGLEBROWSE="/browse/categories/";
	public static String SEVERALBROWSE="/browse/categories";
	
	/****************USERS*************/
	public static String GETUSERPROFILE="/users/sreelatha";
	public static String GETCURRENTUSER="/me";
	public static String GETUSERSTOPITEMS="/me/top/tracks";
	public static String GETFOLLOWEDARTIST="/me/following?type=artist";
	public static String PUTFOLLOWARTIST="/playlists/{id}/followers";
	
	/************PLAYER************/
	public static String GETPLAYBACKSTATE="/me/player";
	public static String GETAVAILABLEDEVICES="/me/player/devices";
	public static String GETCURRENTPLAYINGTRACK="/me/player/currently-playing";
	public static String GETRECENTLYPLAYED="/me/player/recently-played";
	
	/**************TRACK**********/
	public static String GETTRACK="/tracks/";
	public static String GETTRACKSAUDIOFEATURES="/audio-features/";
	public static String GETTRACKAUDIOFEATURESANALYSIS="/audio-analysis/";
	public static String GETUSERSAVEDTRACKS="/me/tracks?q=20";
	public static String GETRECOMMENDATIONS="/recommendations?seed_genres=classical";
	
	/***********PLAYLIST******************/
	//userid=31cg7p6xrybzechkrdiufbz2aro4
	public static String CREATEPLAYLIST="/users/{id}/playlists";
	public static String UPDATEPLAYLIST="/playlists/";
	public static String GETPLAYLIST="/playlists/";
	public static String AddITEMSTOPLAYLIST="/playlists/{id}/tracks";
	public static String GETCURRENTUSERPLAYLIST="/me/playlists";
	public static String GETUSERPLAYLIST="/users/{id}/playlists";
	public static String GETFEATUREDPLAYLIST="/browse/featured-playlists";
	public static String GETCATEGORYPLAYLIST="/browse/categories/{id}/playlists/";
	public static String GETPLAYLISTCOVERIMAGE="/playlists/{id}/images";
	public static String GETSHOWS="/shows/";
	public static String GETSHOWSANDEPISODES="/shows/{id}/episodes";
	public static String GETSAVEDSHOWS="/me/shows";
	public static String GETEPISODES="/episodes/";
	public static String CHECKUSERSAVEDEPISODES="/me/episodes/contains";
	public static String GETSEVERALSHOWS="/shows";
	public static String PUTSAVEEPISODES="/me/episodes";

	
}
