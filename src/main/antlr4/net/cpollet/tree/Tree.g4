grammar Tree;

CHARS: ( [-_.0-9a-zA-Z/] | '\\ ' )+ ;
WS: [ \t\r\n\u000C]+ -> channel(HIDDEN) ;
LINE_COMMENT: '//' ~[\r\n]* -> channel(HIDDEN) ;

node: '(' nodeName=CHARS ',' nodeType=CHARS (',' attrs)? node* ')' ;

attr: key=CHARS '=' value=CHARS ;

attrs: attr (',' attrs)? ;