
package com.example.online_examination;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ExamController {

    private final List<Map<String, Object>> questions = List.of(

        Map.of(
            "id", 1,
            "question", "Which language is used for Android development?",
            "options", Map.of(
                "A", "Java",
                "B", "HTML",
                "C", "CSS",
                "D", "SQL"
            )
        ),

        Map.of(
            "id", 2,
            "question", "Which keyword is used to inherit a class in Java?",
            "options", Map.of(
                "A", "implements",
                "B", "extends",
                "C", "import",
                "D", "package"
            )
        ),

        Map.of(
            "id", 3,
            "question", "Which method is the entry point of a Java program?",
            "options", Map.of(
                "A", "start()",
                "B", "run()",
                "C", "main()",
                "D", "execute()"
            )
        ),

        Map.of(
            "id", 4,
            "question", "Which collection does not allow duplicate elements?",
            "options", Map.of(
                "A", "List",
                "B", "ArrayList",
                "C", "Set",
                "D", "Vector"
            )
        ),

        Map.of(
            "id", 5,
            "question", "Which keyword is used to create an object in Java?",
            "options", Map.of(
                "A", "class",
                "B", "new",
                "C", "this",
                "D", "static"
            )
        )
    );

    private final String[] correctAnswers = {
        "A", "B", "C", "C", "B"
    };

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/exam")
    public String showExam(Model model) {
        model.addAttribute("questions", questions);
        return "exam";
    }

    @PostMapping("/submit")
    public String submitExam(
            @RequestParam String studentName,
            @RequestParam String email,
            @RequestParam Map<String, String> answers,
            Model model) {

        int score = 0;

        for (int i = 0; i < correctAnswers.length; i++) {
            String selectedAnswer = answers.get("q" + (i + 1));

            if (correctAnswers[i].equals(selectedAnswer)) {
                score++;
            }
        }

        double percentage = (score / 5.0) * 100;

        model.addAttribute("studentName", studentName);
        model.addAttribute("email", email);
        model.addAttribute("score", score);
        model.addAttribute("percentage", percentage);
        model.addAttribute("totalQuestions", 5);
        model.addAttribute("result",
                percentage >= 40 ? "PASS" : "FAIL");

        return "result";
    }
}
