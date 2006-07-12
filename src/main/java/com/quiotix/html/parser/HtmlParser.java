/* Generated By:JavaCC: Do not edit this line. HtmlParser.java */
package com.quiotix.html.parser;

/**
 * This grammar parses an HTML document and produces a (flat) parse "tree" 
 * representing the document.  It preserves almost all information in the
 * source document, including carriage control and spacing (except inside
 * of tags.)  See the HtmlDocument and HtmlDocument.* classes for a 
 * description of the parse tree.  The parse tree supports traversal using
 * the commonly used "Visitor" pattern.  The HtmlDumper class is a visitor
 * which dumps out the tree to an output stream.  
 * 
 * It does not require begin tags to be matched with end tags, or validate
 * the names or contents of the tags (this can easily be done post-parsing; 
 * see the HtmlCollector class (which matches begin tags with end tags) 
 * for an example.)  
 * 
 * Notable edge cases include: 
 * - Quoted string processing.  Quoted strings are matched inside of comments, and
 *   as tag attribute values.  Quoted strings are matched in normal text only
 *   to the extent that they do not span line breaks.  
 * 
 * Please direct comments, questions, gripes or praise to 
 * html-parser@quiotix.com.  If you like it/hate it/use it, please let us know!  
 */
public class HtmlParser implements HtmlParserConstants {

  final static String NL = System.getProperty("line.separator");

  private static String getTokenText(Token first, Token cur) {
    Token t;
    StringBuffer sb = new StringBuffer();

    for (t=first; t != cur.next; t = t.next) {
      if (t.specialToken != null) {
        Token tt=t.specialToken;
        while (tt.specialToken != null)
          tt = tt.specialToken;
        for (; tt != null; tt = tt.next)
          sb.append(tt.image);
      };
      sb.append(t.image);
    };
    return sb.toString();
  }

  public static void main(String[] args) throws ParseException {
    HtmlParser parser = new HtmlParser(System.in);
    HtmlDocument doc = parser.HtmlDocument();
    doc.accept(new HtmlDumper(System.out));
    System.exit(0);
  }

  final public HtmlDocument HtmlDocument() throws ParseException {
  HtmlDocument.ElementSequence s;
    s = ElementSequence();
    jj_consume_token(0);
    {if (true) return new HtmlDocument(s);}
    throw new Error("Missing return statement in function");
  }

  final public HtmlDocument.ElementSequence ElementSequence() throws ParseException {
  HtmlDocument.ElementSequence s = new HtmlDocument.ElementSequence();
  HtmlDocument.HtmlElement h;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EOL:
      case TAG_START:
      case ENDTAG_START:
      case COMMENT_START:
      case DECL_START:
      case PCDATA:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      h = Element();
                  s.addElement(h);
    }
    {if (true) return s;}
    throw new Error("Missing return statement in function");
  }

  final public HtmlDocument.HtmlElement Element() throws ParseException {
  HtmlDocument.HtmlElement e;
  Token text;
    if (jj_2_1(2)) {
      e = Tag();
                            {if (true) return e;}
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ENDTAG_START:
        e = EndTag();
                            {if (true) return e;}
        break;
      case COMMENT_START:
        e = CommentTag();
                            {if (true) return e;}
        break;
      case DECL_START:
        e = DeclTag();
                            {if (true) return e;}
        break;
      default:
        jj_la1[1] = jj_gen;
        if (jj_2_2(2)) {
          e = ScriptBlock();
                             {if (true) return e;}
        } else if (jj_2_3(2)) {
          e = StyleBlock();
                             {if (true) return e;}
        } else if (jj_2_4(2)) {
          jj_consume_token(TAG_START);
          text = jj_consume_token(LST_ERROR);
                            {if (true) return new HtmlDocument.Text("<" + text.image);}
        } else {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case PCDATA:
            text = jj_consume_token(PCDATA);
                            {if (true) return new HtmlDocument.Text(text.image);}
            break;
          case EOL:
            jj_consume_token(EOL);
                            {if (true) return new HtmlDocument.Newline();}
            break;
          default:
            jj_la1[2] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
      }
    }
    throw new Error("Missing return statement in function");
  }

  final public HtmlDocument.Attribute Attribute() throws ParseException {
  HtmlDocument.Attribute a;
  Token t1, t2=null;
    t1 = jj_consume_token(ATTR_NAME);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ATTR_EQ:
      jj_consume_token(ATTR_EQ);
      t2 = jj_consume_token(ATTR_VAL);
      break;
    default:
      jj_la1[3] = jj_gen;
      ;
    }
    if (t2 == null)
      {if (true) return new HtmlDocument.Attribute(t1.image);}
    else
      {if (true) return new HtmlDocument.Attribute(t1.image, t2.image);}
    throw new Error("Missing return statement in function");
  }

  final public HtmlDocument.AttributeList AttributeList() throws ParseException {
  HtmlDocument.AttributeList alist = new HtmlDocument.AttributeList();
  HtmlDocument.Attribute a;
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ATTR_NAME:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_2;
      }
      a = Attribute();
                   alist.addAttribute(a);
    }
    {if (true) return alist;}
    throw new Error("Missing return statement in function");
  }

  final public HtmlDocument.HtmlElement Tag() throws ParseException {
  Token t, et;
  HtmlDocument.AttributeList alist;
  Token firstToken = getToken(1);
    try {
      jj_consume_token(TAG_START);
      t = jj_consume_token(TAG_NAME);
      alist = AttributeList();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TAG_END:
        et = jj_consume_token(TAG_END);
        break;
      case TAG_SLASHEND:
        et = jj_consume_token(TAG_SLASHEND);
        break;
      default:
        jj_la1[5] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      HtmlDocument.Tag tag = new HtmlDocument.Tag(t.image, alist);
      if (et.kind == TAG_SLASHEND) tag.setEmpty(true);
      {if (true) return tag;}
    } catch (ParseException ex) {
    token_source.SwitchTo(DEFAULT);
    String s = getTokenText(firstToken, getNextToken());
    {if (true) return new HtmlDocument.Text(s);}
    }
    throw new Error("Missing return statement in function");
  }

  final public HtmlDocument.ElementSequence BlockContents() throws ParseException {
  Token t;
  StringBuffer s = new StringBuffer();
  HtmlDocument.ElementSequence e = new HtmlDocument.ElementSequence();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BLOCK_EOL:
      case BLOCK_LBR:
      case BLOCK_WORD:
        ;
        break;
      default:
        jj_la1[6] = jj_gen;
        break label_3;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BLOCK_EOL:
        jj_consume_token(BLOCK_EOL);
      if (s.length() > 0) {
        e.addElement(new HtmlDocument.Text(s.toString()));
        s.setLength(0);
      };
      e.addElement(new HtmlDocument.Newline());
        break;
      case BLOCK_WORD:
        t = jj_consume_token(BLOCK_WORD);
                       s.append(t.image);
        break;
      case BLOCK_LBR:
        t = jj_consume_token(BLOCK_LBR);
                       s.append(t.image);
        break;
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    if (s.length() > 0)
      e.addElement(new HtmlDocument.Text(s.toString()));
    e.addElement(new HtmlDocument.Newline());
    {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  final public HtmlDocument.HtmlElement ScriptBlock() throws ParseException {
  HtmlDocument.AttributeList alist;
  HtmlDocument.ElementSequence e;
  Token firstToken = getToken(1);
    try {
      jj_consume_token(TAG_START);
      jj_consume_token(TAG_SCRIPT);
      alist = AttributeList();
      jj_consume_token(TAG_END);
      token_source.SwitchTo(LexScript);
      e = BlockContents();
      jj_consume_token(SCRIPT_END);
      {if (true) return new HtmlDocument.TagBlock("SCRIPT", alist, e);}
    } catch (ParseException ex) {
    token_source.SwitchTo(DEFAULT);
    String s = getTokenText(firstToken, getNextToken());
    {if (true) return new HtmlDocument.Text(s);}
    }
    throw new Error("Missing return statement in function");
  }

  final public HtmlDocument.HtmlElement StyleBlock() throws ParseException {
  HtmlDocument.AttributeList alist;
  HtmlDocument.ElementSequence e;
  Token firstToken = getToken(1);
    try {
      jj_consume_token(TAG_START);
      jj_consume_token(TAG_STYLE);
      alist = AttributeList();
      jj_consume_token(TAG_END);
      token_source.SwitchTo(LexStyle);
      e = BlockContents();
      jj_consume_token(STYLE_END);
      {if (true) return new HtmlDocument.TagBlock("STYLE", alist, e);}
    } catch (ParseException ex) {
    token_source.SwitchTo(DEFAULT);
    String s = getTokenText(firstToken, getNextToken());
    {if (true) return new HtmlDocument.Text(s);}
    }
    throw new Error("Missing return statement in function");
  }

  final public HtmlDocument.HtmlElement EndTag() throws ParseException {
  Token t;
  Token firstToken = getToken(1);
    try {
      jj_consume_token(ENDTAG_START);
      t = jj_consume_token(TAG_NAME);
      jj_consume_token(TAG_END);
      {if (true) return new HtmlDocument.EndTag(t.image);}
    } catch (ParseException ex) {
    token_source.SwitchTo(DEFAULT);
    String s = getTokenText(firstToken, getNextToken());
    {if (true) return new HtmlDocument.Text(s);}
    }
    throw new Error("Missing return statement in function");
  }

  final public HtmlDocument.Comment CommentTag() throws ParseException {
  Token t;
  StringBuffer s = new StringBuffer("--");
    jj_consume_token(COMMENT_START);
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DASH:
      case COMMENT_EOL:
      case COMMENT_WORD:
        ;
        break;
      default:
        jj_la1[8] = jj_gen;
        break label_4;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DASH:
        t = jj_consume_token(DASH);
               s.append(t.image);
        break;
      case COMMENT_EOL:
        jj_consume_token(COMMENT_EOL);
                       s.append(NL);
        break;
      case COMMENT_WORD:
        t = jj_consume_token(COMMENT_WORD);
                         s.append(t.image);
        break;
      default:
        jj_la1[9] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 0:
      jj_consume_token(0);
      break;
    case COMMENT_END:
      jj_consume_token(COMMENT_END);
      break;
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return new HtmlDocument.Comment(s.append("--").toString());}
    throw new Error("Missing return statement in function");
  }

  final public HtmlDocument.Comment DeclTag() throws ParseException {
  Token t;
    jj_consume_token(DECL_START);
    t = jj_consume_token(DECL_ANY);
    jj_consume_token(DECL_END);
    {if (true) return new HtmlDocument.Comment(t.image);}
    throw new Error("Missing return statement in function");
  }

  final private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  final private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  final private boolean jj_2_3(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  final private boolean jj_2_4(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_4(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  final private boolean jj_3_2() {
    if (jj_3R_6()) return true;
    return false;
  }

  final private boolean jj_3R_5() {
    if (jj_scan_token(TAG_START)) return true;
    if (jj_scan_token(TAG_NAME)) return true;
    return false;
  }

  final private boolean jj_3_1() {
    if (jj_3R_5()) return true;
    return false;
  }

  final private boolean jj_3R_7() {
    if (jj_scan_token(TAG_START)) return true;
    if (jj_scan_token(TAG_STYLE)) return true;
    return false;
  }

  final private boolean jj_3_4() {
    if (jj_scan_token(TAG_START)) return true;
    if (jj_scan_token(LST_ERROR)) return true;
    return false;
  }

  final private boolean jj_3_3() {
    if (jj_3R_7()) return true;
    return false;
  }

  final private boolean jj_3R_6() {
    if (jj_scan_token(TAG_START)) return true;
    if (jj_scan_token(TAG_SCRIPT)) return true;
    return false;
  }

  public HtmlParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  public Token token, jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  public boolean lookingAhead = false;
  private boolean jj_semLA;
  private int jj_gen;
  final private int[] jj_la1 = new int[11];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_0();
      jj_la1_1();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0x1f800,0xe000,0x10800,0x2000000,0x400000,0x1800000,0x0,0x0,0x0,0x0,0x80000001,};
   }
   private static void jj_la1_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x380,0x380,0x7,0x7,0x0,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[4];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  public HtmlParser(java.io.InputStream stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new HtmlParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(java.io.InputStream stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public HtmlParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new HtmlParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public HtmlParser(HtmlParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(HtmlParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  final private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }

  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  final public Token getToken(int index) {
    Token t = lookingAhead ? jj_scanpos : token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.Vector jj_expentries = new java.util.Vector();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      boolean exists = false;
      for (java.util.Enumeration e = jj_expentries.elements(); e.hasMoreElements();) {
        int[] oldentry = (int[])(e.nextElement());
        if (oldentry.length == jj_expentry.length) {
          exists = true;
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              exists = false;
              break;
            }
          }
          if (exists) break;
        }
      }
      if (!exists) jj_expentries.addElement(jj_expentry);
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[42];
    for (int i = 0; i < 42; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 11; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 42; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  final public void enable_tracing() {
  }

  final public void disable_tracing() {
  }

  final private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 4; i++) {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
            case 2: jj_3_3(); break;
            case 3: jj_3_4(); break;
          }
        }
        p = p.next;
      } while (p != null);
    }
    jj_rescan = false;
  }

  final private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
