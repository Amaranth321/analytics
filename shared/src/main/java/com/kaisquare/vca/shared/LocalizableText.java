package com.kaisquare.vca.shared;

import com.kaisquare.vca.utils.SharedUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class LocalizableText
{
    private final Map<String, String> textMap;

    public static LocalizableText create(String englishText)
    {
        LocalizableText newInst = new LocalizableText();
        newInst.add("en", englishText);
        return newInst;
    }

    public static LocalizableText create(Map<String, String> textMap)
    {
        LocalizableText newInst = new LocalizableText();
        newInst.textMap.putAll(textMap);
        return newInst;
    }

    public LocalizableText add(String languageCode, String localizedText)
    {
        if (SharedUtils.isNullOrEmpty(languageCode) || SharedUtils.isNullOrEmpty(localizedText))
        {
            throw new NullPointerException(languageCode + ":" + localizedText);
        }
        textMap.put(languageCode, localizedText);
        return this;
    }

    public String get(String languageCode)
    {
        return textMap.containsKey(languageCode) ? textMap.get(languageCode) : textMap.get("en");
    }

    public Map<String, String> map()
    {
        return new LinkedHashMap<>(textMap);
    }

    private LocalizableText()
    {
        this.textMap = new LinkedHashMap<>();
    }
}
