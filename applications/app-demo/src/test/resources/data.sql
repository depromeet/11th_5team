insert into member
    (member_id, identify_token, nickname, profile_img)
values (1, 'profileImg', 'nickname', 'identifyToken');

insert into posts
(id, content, created_at, updated_at, disclosure, first_category, member_id, second_category, tags, views)
values ('1', 'content1', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1, 'SADNESS', 1, 'SADNESS', 'tag1,tag2', 0);

insert into posts
(id, content, created_at, updated_at, disclosure, first_category, member_id, second_category, tags, views)
values ('2', 'content2', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1, 'SADNESS', 1, 'SADNESS', 'tag1,tag2', 0);

insert into posts
(id, content, created_at, updated_at, disclosure, first_category, member_id, second_category, tags, views)
values ('3', 'content3', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1, 'SADNESS', 1, 'Unwritten', 'tag1,tag2', 0);

insert into folder
(folder_id, created_at, updated_at, cover_img, is_default, member_id, name)
values (3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'default cover', 1, 1, '미분류 폴더');

insert into folder
(folder_id, created_at, updated_at, cover_img, is_default, member_id, name)
values (1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'cover image', 0, 1, 'folder name');

insert into folder
(folder_id, created_at, updated_at, cover_img, is_default, member_id, name)
values (2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'cover image2', 0, 1, 'folder name2');

insert into folder_item
(folder_item_id, created_at, updated_at, content, disclosure, first_category, member_id, post_id, second_category, tags,
 views, folder_id)
values ('1', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'post content', 1, 'SADNESS', 1, '1', 'SADNESS', 'tag1,tag2', 12,
        1);

insert into folder_item
(folder_item_id, created_at, updated_at, content, disclosure, first_category, member_id, post_id, second_category, tags,
 views, folder_id)
values ('2', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'post content', 1, 'SADNESS', 1, '2', 'SADNESS', 'tag1,tag2', 21,
        1);

insert into folder_item
(folder_item_id, created_at, updated_at, content, disclosure, first_category, member_id, post_id, second_category, tags,
 views, folder_id)
values ('3', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'post content', 1, 'SADNESS', 1, '3', 'SADNESS', 'tag1,tag2', 3,
        1);