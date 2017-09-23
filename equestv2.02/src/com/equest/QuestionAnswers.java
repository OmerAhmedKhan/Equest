package com.equest;

import java.util.ArrayList;

public class QuestionAnswers {
	String questions; // string to hold question
	String questionId;// string to hold question id
	static String timeOfTest; // a string that holds time of test and is
								// constant for every object of class
	ArrayList<String> answerId; // array list to store
	static int countAnswerOptions; // no of answer options
	ArrayList<String> isCorrect; // list to hold which answer is correct
	ArrayList<String> answer;// list to hold answers
	String isImageQuestion; // String to tell whether the question contains
							// image or not
	ArrayList<String> isImageAnswer; // list to hold image of each option

	ArrayList<String> ImageAnswer;
	ArrayList<String> ImageQuestion;

	String imageQuestion; // String to hold the image URL

	static int countQuestions;

	public QuestionAnswers() {
		answer = new ArrayList<String>();
		isCorrect = new ArrayList<String>();
		isImageAnswer = new ArrayList<String>();
		answerId = new ArrayList<String>();
		countQuestions = 0;
	}

	// set time of test
	public static void setTimeOfTest(String time) {
		timeOfTest = time;
	}

	// return time of test
	public static int retTimeOfTest() {
		int time;
		time = Integer.parseInt(timeOfTest);
		time = time * 60 * 1000;
		return time;
	}

	// set total no of questions
	public static void setTotalQuestions(int count) {
		countQuestions = count;
	}

	// set total number of answer options in a question
	public static void setCountAnswerOptions(int count) {
		countAnswerOptions = count;
	}

	// sets the image URL
	public void setImageQuestion(String image) {
		imageQuestion = image;
	}

	// return URL of question image
	public String retQuestionImage() {
		return imageQuestion;
	}

	// return image of answer option of the given index
	public String retAnswerImage(int index) {
		return isImageAnswer.get(index);
	}

	// return the total no of options in an answer
	public static int retCountAnswerOption() {
		return countAnswerOptions;
	}

	// sets question id
	public void setQuestionId(String id) {
		questionId = id;
	}

	// sets answer id
	public void setAnswerId(String id) {
		answerId.add(id);
	}

	// returns total no of questions
	public static int retCountQuestions() {
		return countQuestions;
	}

	// sets question,and attribute that tells wheter the question is an image or
	// not
	public void setQuestions(String Quest, String isImage) {
		questions = Quest;
		isImageQuestion = isImage;
	}

	// sets answers, whether the answer is correct or not and the image of the
	// answer
	public void setAnswers(String Ans, String correct, String isImage) {
		answer.add(Ans);
		isCorrect.add(correct);
		isImageAnswer.add(isImage);
	}

	// return whether the question is an image or not
	public String retIsImageQuestion() {
		return isImageQuestion;
	}

	public String retIsImageAnswer(int index) {
		return isImageAnswer.get(index);
	}

	// returns question
	public String retQuestion() {
		return questions;
	}

	// returns answer id
	public String retAnswerId(int index) {
		return answerId.get(index);
	}

	// returns answer
	public String retAnswer(int index) {
		return answer.get(index);
	}

	// return if the particular option is correct or not
	public String retIsCorrect(int index) {
		return isCorrect.get(index);
	}

	// returns question id
	public String retQuestionId() {
		return questionId;
	}

	// overridden toString method
	@Override
	public String toString() {
		return "QuestionAnswers [questions=" + questions + ", questionId="
				+ questionId + ", answerId=" + answerId + ", isCorrect="
				+ isCorrect + ", answer=" + answer + ", isImageQuestion="
				+ isImageQuestion + ", isImageAnswer=" + isImageAnswer
				+ ", imageQuestion=" + imageQuestion + "]";
	}

}
