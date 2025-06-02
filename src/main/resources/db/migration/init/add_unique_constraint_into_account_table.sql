use sideproject;

alter table account
add constraint unique username_unq(username),
add constraint unique email_unq(email);