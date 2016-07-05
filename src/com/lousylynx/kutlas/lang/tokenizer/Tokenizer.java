package com.lousylynx.kutlas.lang.tokenizer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    private ArrayList<TokenData> tokenDatas;

    private String str;

    private Token lastToken;
    private boolean pushBack;

    @SuppressWarnings("all")
    public Tokenizer(String str)
    {
        this.tokenDatas = new ArrayList<>();
        this.str = str;

        tokenDatas.add(new TokenData(Pattern.compile("\".*\"|([^\\W]+)"), TokenType.IDENTIFIER));
        tokenDatas.add(new TokenData(Pattern.compile("\"[^\"]*\""), TokenType.STRING_LITERAL));
        tokenDatas.add(new TokenData(Pattern.compile("(-)?[0-9]"), TokenType.INTEGER));

        for(String t : new String[] { "=", "\\(", "\\)", "\\,", "\\{", "\\}", ":", "\\.\\.\\.", "\\." })
        {
            tokenDatas.add(new TokenData(Pattern.compile(t), TokenType.TOKEN));
        }
    }

    public Token nextToken()
    {
        str = str.trim();

        if(pushBack)
        {
            pushBack = false;
            return lastToken;
        }

        if(str.isEmpty())
        {
            return (lastToken = new Token("", TokenType.EMPTY));
        }

        for(TokenData data : tokenDatas)
        {
            Matcher matcher = data.getPattern().matcher(str);

            if(matcher.find())
            {
                String token = matcher.group().trim();
                str = matcher.replaceFirst("");

                if(token.startsWith("\"") && token.endsWith("\""))
                {
                    return (lastToken = new Token(token.substring(1, token.length() - 1), TokenType.STRING_LITERAL));
                }else{
                    return (lastToken = new Token(token, data.getType()));
                }
            }
        }

        throw new IllegalStateException("Could not parse " + str);
    }

    public boolean hasNextToken()
    {
        return !str.isEmpty();
    }

    public void pushBack()
    {
        if(lastToken != null)
        {
            this.pushBack = true;
        }
    }
}
