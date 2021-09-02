package com.fullvicie.enums;

public enum ErrorType {
	
	NO_ERROR,
	
		
	/*
	 * USABILITY ERRORS
	 */
	NULL_PARAMETER_ERROR,
	LOGIN_ERROR,
	PASSWORDS_DOES_NOT_MATCHES_ERROR,
	ACCESS_DENIED_ERROR,
	
	ACTION_DOES_NOT_EXIST_ERROR,
	SELECTED_OPTION_DOES_NOT_EXIST_ERROR,
	
	/*
	 * DATABASE ERRORS TYPE
	 */
	DATABASE_STATEMENT_ERROR,
	DATABASE_CONNECTION_ERROR,
	DATABASE_DISCONNECTION_ERROR,
	
	
	/*
	 * OBJECT ALREADY EXISTS ERRORS TYPE
	 */
	ASSESSMENT_ALREADY_EXISTS_ERROR,
	BILL_ALREADY_EXISTS_ERROR,
	CATEGORY_ALREADY_EXISTS_ERROR,
	FORUM_ALREADY_EXISTS_ERROR,
	FORUM_CATEGORY_ALREADY_EXISTS_ERROR,
	FORUM_MESSAGE_ALREADY_EXISTS_ERROR,
	FORUM_MESSAGE_LIKE_ALREADY_EXISTS_ERROR,
	PLAYER_ALREADY_EXISTS_ERROR,
	POST_ALREADY_EXISTS_ERROR,
	POST_CATEGORY_ALREADY_EXISTS_ERROR,
	POST_COMMENT_ALREADY_EXISTS_ERROR,
	POST_COMMENT_LIKE_ALREADY_EXISTS_ERROR,
	POST_LIKE_ALREADY_EXISTS_ERROR,
	PROFILE_ALREADY_EXISTS_ERROR,
	PURCHASE_ALREADY_EXISTS_ERROR,
	RENTAL_ALREADY_EXISTS_ERROR,
	REPORT_ALREADY_EXISTS_ERROR,
	TEAM_ALREADY_EXISTS_ERROR,
	USER_ALREADY_EXISTS_ERROR,
	VIDEOGAME_ALREADY_EXISTS_ERROR,
	VIDEOGAME_IMAGE_ALREADY_EXISTS_ERROR,
	VIDEOGAME_CATEGORY_ALREADY_EXISTS_ERROR,
	
	
	/*
	 * OBJECT DOES NOT EXIST ERRORS TYPE
	 */
	ASSESSMENT_DOES_NOT_EXIST_ERROR,
	BILL_DOES_NOT_EXIST_ERROR,
	CATEGORY_DOES_NOT_EXIST_ERROR,
	FORUM_DOES_NOT_EXIST_ERROR,
	FORUM_CATEGORY_DOES_NOT_EXIST_ERROR,
	FORUM_MESSAGE_DOES_NOT_EXIST_ERROR,
	FORUM_MESSAGE_LIKE_DOES_NOT_EXIST_ERROR,
	PLAYER_DOES_NOT_EXIST_ERROR,
	POST_DOES_NOT_EXIST_ERROR,
	POST_CATEGORY_DOES_NOT_EXIST_ERROR,
	POST_COMMENT_DOES_NOT_EXIST_ERROR,
	POST_COMMENT_LIKE_EDOES_NOT_EXIST_RROR,
	POST_LIKE_DOES_NOT_EXIST_ERROR,
	PROFILE_DOES_NOT_EXIST_ERROR,
	PURCHASE_DOES_NOT_EXIST_ERROR,
	RENTAL_DOES_NOT_EXIST_ERROR,
	REPORT_DOES_NOT_EXIST_ERROR,
	TEAM_DOES_NOT_EXIST_ERROR,
	USER_DOES_NOT_EXIST_ERROR,
	VIDEOGAME_DOES_NOT_EXIST_ERROR,
	VIDEOGAME_IMAGE_DOES_NOT_EXIST_ERROR,
	VIDEOGAME_CATEGORY_DOES_NOT_EXIST_ERROR,
	
	
	/*
	 * CREATE OBJECT ERRORS TYPE
	 */
	CREATE_ASSESSMENT_ERROR,
	CREATE_BILL_ERROR,
	CREATE_CATEGORY_ERROR,
	CREATE_FORUM_ERROR,
	CREATE_FORUM_CATEGORY_ERROR,
	CREATE_FORUM_MESSAGE_ERROR,
	CREATE_FORUM_MESSAGE_LIKE_ERROR,
	CREATE_PLAYER_ERROR,
	CREATE_POST_ERROR,
	CREATE_POST_CATEGORY_ERROR,
	CREATE_POST_COMMENT_ERROR,
	CREATE_POST_COMMENT_LIKE_ERROR,
	CREATE_POST_LIKE_ERROR,
	CREATE_PROFILE_ERROR,
	CREATE_PURCHASE_ERROR,
	CREATE_RENTAL_ERROR,
	CREATE_REPORT_ERROR,
	CREATE_TEAM_ERROR,
	CREATE_USER_ERROR,
	CREATE_VIDEOGAME_ERROR,
	CREATE_VIDEOGAME_IMAGE_ERROR,
	CREATE_VIDEOGAME_CATEGORY_ERROR,
	
	
	
	/*
	 * READ OBJECT ERRORS TYPE
	 */
	READ_ASSESSMENT_ERROR,
	READ_BILL_ERROR,
	READ_CATEGORY_ERROR,
	READ_FORUM_ERROR,
	READ_FORUM_CATEGORY_ERROR,
	READ_FORUM_MESSAGE_ERROR,
	READ_FORUM_MESSAGE_LIKE_ERROR,
	READ_PLAYER_ERROR,
	READ_POST_ERROR,
	READ_POST_CATEGORY_ERROR,
	READ_POST_COMMENT_ERROR,
	READ_POST_COMMENT_LIKE_ERROR,
	READ_POST_LIKE_ERROR,
	READ_PROFILE_ERROR,
	READ_PURCHASE_ERROR,
	READ_RENTAL_ERROR,
	READ_REPORT_ERROR,
	READ_TEAM_ERROR,
	READ_USER_ERROR,
	READ_VIDEOGAME_ERROR,
	READ_VIDEOGAME_IMAGE_ERROR,
	READ_VIDEOGAME_CATEGORY_ERROR,
	
	
	/*
	 * UPDATE OBJECT ERRORS TYPE
	 */
	UPDATE_ASSESSMENT_ERROR,
	UPDATE_BILL_ERROR,
	UPDATE_CATEGORY_ERROR,
	UPDATE_FORUM_ERROR,
	UPDATE_FORUM_CATEGORY_ERROR,
	UPDATE_FORUM_MESSAGE_ERROR,
	UPDATE_FORUM_MESSAGE_LIKE_ERROR,
	UPDATE_PLAYER_ERROR,
	UPDATE_POST_ERROR,
	UPDATE_POST_CATEGORY_ERROR,
	UPDATE_POST_COMMENT_ERROR,
	UPDATE_POST_COMMENT_LIKE_ERROR,
	UPDATE_POST_LIKE_ERROR,
	UPDATE_PROFILE_ERROR,
	UPDATE_PURCHASE_ERROR,
	UPDATE_RENTAL_ERROR,
	UPDATE_REPORT_ERROR,
	UPDATE_TEAM_ERROR,
	UPDATE_USER_ERROR,
	UPDATE_VIDEOGAME_ERROR,
	UPDATE_VIDEOGAME_IMAGE_ERROR,
	UPDATE_VIDEOGAME_CATEGORY_ERROR,
	
	
	/*
	 * DELETE OBJECT ERRORS TYPE
	 */
	DELETE_ASSESSMENT_ERROR,
	DELETE_BILL_ERROR,
	DELETE_CATEGORY_ERROR,
	DELETE_FORUM_ERROR,
	DELETE_FORUM_CATEGORY_ERROR,
	DELETE_FORUM_MESSAGE_ERROR,
	DELETE_FORUM_MESSAGE_LIKE_ERROR,
	DELETE_PLAYER_ERROR,
	DELETE_POST_ERROR,
	DELETE_POST_CATEGORY_ERROR,
	DELETE_POST_COMMENT_ERROR,
	DELETE_POST_COMMENT_LIKE_ERROR,
	DELETE_POST_LIKE_ERROR,
	DELETE_PROFILE_ERROR,
	DELETE_PURCHASE_ERROR,
	DELETE_RENTAL_ERROR,
	DELETE_REPORT_ERROR,
	DELETE_TEAM_ERROR,
	DELETE_USER_ERROR,
	DELETE_VIDEOGAME_ERROR,
	DELETE_VIDEOGAME_IMAGE_ERROR,
	DELETE_VIDEOGAME_CATEGORY_ERROR,
	
	/*
	 * NULL ERRORS TYPE
	 */
	USER_NULL_ERROR,
	
	/*
	 * ENCRYPTOR ERRORS TYPE
	 */
	ENCRYPT_PASSWORD_ERROR,
	DECRYPT_PASSWORD_ERROR,
	CREATING_ENCRYPTOR_KEY_ERROR, 
	
	PROFILE_NULL_ERROR,
	TEAM_NULL_ERROR,
	PLAYER_NULL_ERROR, UPDATE_GAMER_PROFILE_ERROR;
	
}
