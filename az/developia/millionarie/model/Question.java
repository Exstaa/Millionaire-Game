package az.developia.millionarie.model;

import java.util.Arrays;

public class Question {
	private Integer id;
	private String question;
	private String[] variants;
	private String answer;

	public Question() {

	}

	public Question(Integer id, String question, String[] variants, String answer) {
		this.id = id;
		this.question = question;
		this.variants = variants;
		this.answer = answer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String[] getVariants() {
		return variants;
	}

	public void setVariants(String[] variants) {
		this.variants = variants;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", question=" + question + ", variants=" + Arrays.toString(variants) + ", answer="
				+ answer + "]";
	}

}
