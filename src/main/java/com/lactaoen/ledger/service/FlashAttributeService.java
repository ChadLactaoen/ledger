package com.lactaoen.ledger.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class FlashAttributeService {

    public FlashAttributeService() {
    }

    public void generateFlashAttributes(RedirectAttributes model, boolean success, String objectType, PostType postType) {
        if (success) {
            model.addFlashAttribute("msgClass", "success");
            model.addFlashAttribute("msg", "The " + objectType + " was " + postType.pastTense + " successfully");
        } else {
            model.addFlashAttribute("msgClass", "danger");
            model.addFlashAttribute("msg", "There was an issue " + postType.presentTense + " the " + objectType);
        }
    }

    public enum PostType {
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
