package com.lactaoen.ledger.controller.api;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class AbstractApiController {

    protected void generateFlashAttributes(RedirectAttributes model, Integer result, String objectType, PostType postType) {
        if (result != 0) {
            model.addFlashAttribute("msgClass", "success");
            model.addFlashAttribute("msg", "The " + objectType + " was " + postType.pastTense + " successfully");
        } else {
            model.addFlashAttribute("msgClass", "danger");
            model.addFlashAttribute("msg", "There was an issue " + postType.presentTense + " the " + objectType);
        }
    }

    enum PostType {
        ADD("added", "adding"),
        UPDATE("updated", "updating");

        final String pastTense;
        final String presentTense;

        PostType(String pastTense, String presentTense) {
            this.pastTense = pastTense;
            this.presentTense = presentTense;
        }
    }
}
