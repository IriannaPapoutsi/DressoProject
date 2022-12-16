INSERT INTO "User" (firstName, lastName, postalCode, country, city, address, email, credits)
VALUES ('Irianna', 'Papoutsi', '11526', 'Greece', 'Athens', 'Rostoviou 30-32', 'iriannapapoutsi@gmail.com', 15);
INSERT INTO "User" (firstName, lastName, postalCode, country, city, address, email, credits)
VALUES ('Giorgos', 'Lagoudakis', '14683', 'Greece', 'Keratsini', 'Amorgou 45', 'glagoudakis@gmail.com', 15);
INSERT INTO "User" (firstName, lastName, postalCode, country, city, address, email, credits)
VALUES ('Giannis', 'Papoutsis', '14379', 'Greece', 'Herakleion, Crete', 'Pl. Kornarou 27', 'gpapoutsis@gmail.com', 10);
INSERT INTO "User" (firstName, lastName, postalCode, country, city, address, email, credits)
VALUES ('Emmeleia', 'Voutierou', '12567', 'Greece', 'Pireus', 'Thrakis 5', 'evoutierou@gmail.com', 8);
INSERT INTO "User" (firstName, lastName, postalCode, country, city, address, email, credits)
VALUES ('Eirini', 'Papoutsi', '15234', 'Greece', 'Vyronas', 'Flemingk 81', 'epapoutsi@gmail.com', 5);

INSERT INTO Color (name) VALUES ('Purple');
INSERT INTO Color (name) VALUES ('Red');
INSERT INTO Color (name) VALUES ('Green');
INSERT INTO Color (name) VALUES ('Blue');
INSERT INTO Color (name) VALUES ('Yellow');
INSERT INTO Color (name) VALUES ('Black');
INSERT INTO Color (name) VALUES ('White');
INSERT INTO Color (name) VALUES ('Pink');

INSERT INTO Category (name) VALUES ('Coat');
INSERT INTO Category (name) VALUES ('Jeans');
INSERT INTO Category (name) VALUES ('T-shirt');
INSERT INTO Category (name) VALUES ('Dress');
INSERT INTO Category (name) VALUES ('Skirt');
INSERT INTO Category (name) VALUES ('Bags');

INSERT INTO UserLogin (userID, username, password) VALUES ('1', 'iriannapap', 'codehubrocks!');
INSERT INTO UserLogin (userID, username, password) VALUES ('2', 'lagoudakis', 'codehubrocksagain!');
INSERT INTO UserLogin (userID, username, password) VALUES ('3', 'johnny', 'herakleionisthebest!');
INSERT INTO UserLogin (userID, username, password) VALUES ('4', 'eme', 'DETsucks$');
INSERT INTO UserLogin (userID, username, password) VALUES ('5', 'eirini', 'ThinkBiz4ever!');

INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('3', '5', 'Mini Skirt', 29.99, 'Frenchy High Waist Textured Flare Skirt. You can match this item with plenty of blouses, t-shirts or een sweaters since it is an all season to wear skirt!', 'GS234');
INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('1', '5', 'Satin Silk Skirt', 44.99, 'Summer Purple Satin Silk Skirt Elegant Women High Waisted Mid-Calf Skirt Cute Midi Skirt For Office Ladies Chic.', 'PS234');
INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('3', '3', 'Lacoste Polo T-shirt', 34.99, 'Polo T-shirt in green color. An all time classic item which has been loved by young and old alike.', 'PTS234');
INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('1', '4', 'Elegant Dress', 154.99, 'Mermaid style one-shoulder dress crafted from crystal fabric with a front side split design.', 'PD234');
INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('4', '4', 'Summer Dress', 27.50, 'Elegant pleated maxi light blue dress with high waist embedded belt.', 'BD234');
INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('4', '2', 'Cotton Straight Fit Jeans', 37.50, 'These regular fit jeans offer plenty of modern style, ready to mix and match with anything in your wardrobe to lift your look.', 'BJ234');
INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('2', '1', 'Wool-Blend Dad Coat', 240, 'Our classic wool-blend dad coat in a tailored fit with luxe interior lining, side pockets and center button closure.', 'RC234');
INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('2', '6', 'Cleo shoulder bag', 120, 'Cleo brushed leather shoulder bag with sophisticated allure reinterprets an iconic design of the brand from the 1990''s. Sleek curved lines emphasized by the particular construction rounded on the bottom and sides give this flap bag a soft, light look.', 'RBAG234');
INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('5', '6', 'Saffiano leather shoulder bag', 250, 'This shoulder bag with a feminine design and flap is made of Saffiano leather. The accessory is embellished with enameled metal triangle logo that decorates the tip of the flap closure.', 'YBAG234');
INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('5', '3', 'Slim Fit Mesh Polo Shirt', 99, 'An American style standard since 1972, the Polo shirt has been imitated but never matched. Over the decades, Ralph Lauren has re-imagined his signature style in a wide array of colours and fits, yet all retain the quality and attention to detail of the iconic original.', 'YT234');
INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('6', '2', 'Denim Black Jeans', 144.99, 'Diesel Cotton denim of black solid color. High waist modern jeans with 1 button, zipper and multiple pockets. D-akemi model contains parts of non-textile materials of animal origin.', 'BLJ234');
INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('6', '1', 'Saint Laurent masculine wool coat', 344.99, 'Straight-cut coat with covered button placket, featuring a notched collar.', 'BLC234');
INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('7', '2', 'TED BAKER 90''s Flood Length Jeans', 169.99, 'Fashion and function come together in SIMAH of TED BAKER. The 90''s is back and better than ever with these wide leg jeans—pop on a graphic tee and a bomber jacket for an effortless nod to nostalgia.', 'WJ234');
INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('7', '6', 'Jacquemus Le Grand Chiquito leather bag', 344.99, 'Jacquemus Le Grand Chiquito white leather bag. Pre-FW 2020 sees a return of the bag that upset the stereotype of soft, oversized women’s bags- the infamous Le Chiquito- in a brand new range of colorways. This season a record number of new bag silhouettes are also introduced, from surprisingly normal-sized tote bags to wicker versions of the Le Chiquito which replace the thin original leather shoulder strap with a large one made of fabric and with silver metal hooks.', 'JLCBAG234');
INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('2', '3', 'Lacoste Polo T-shirt', 34.99, 'Polo T-shirt in red color. An all time classic item which has been loved by young and old alike.', 'PTS234');
INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('1', '3', 'Lacoste Polo T-shirt', 34.99, 'Polo T-shirt in a special purple color. An all time classic item which has been loved by young and old alike.', 'PTS234');
INSERT INTO Product (colorID, categoryID, name, price, description, sku) VALUES ('8', '6', 'Jacquemus Le Grand Chiquito leather bag', 344.99, 'Jacquemus Le Grand Chiquito pink leather bag. Pre-FW 2020 sees a return of the bag that upset the stereotype of soft, oversized women’s bags- the infamous Le Chiquito- in a brand new range of colorways. This season a record number of new bag silhouettes are also introduced, from surprisingly normal-sized tote bags to wicker versions of the Le Chiquito which replace the thin original leather shoulder strap with a large one made of fabric and with silver metal hooks.', 'JLCBAG234');

INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('1', 'Green Mini Skirt', 'https://img.ltwebstatic.com/images3_pi/2022/09/19/1663553698ec9daa02beeed0c0c82b556f6024dbce_thumbnail_900x.webp');
INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('2', 'Purple Satin Skirt', 'https://ae01.alicdn.com/kf/Hcd256fec269443bd9f95cb668356ed0dI/Summer-Purple-Satin-Silk-Skirt-Elegant-Women-High-Waisted-Mid-Calf-Skirt-Cute-Midi-Skirt-For.jpg_Q90.jpg_.webp');
INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('3', 'Polo T-shirt', 'https://shoemania21.com/wp-content/uploads/2022/12/sc_007544_a.jpeg');
INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('4', 'Maxi Dress', 'https://www.nashbyna.com/wp-content/uploads/2022/01/nash-02-scarlet-purple-skouro-mov-forema-winter-summer-collection-nashbyna-natashaavloniti-greekdesigner-weddingdresses-episima-foremata-vradina-foremata-1.jpg');
INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('5', 'Maxi Mermaid Dress', 'https://img.ltwebstatic.com/images3_pi/2021/11/05/1636109378ffcd04546b60bc31eca5bf6c5363283e_thumbnail_900x.webp');
INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('6', 'Blue Jeans', 'https://asset1.cxnmarksandspencer.com/is/image/mands/Pure-Cotton-Straight-Fit-Jeans-3/SD_03_T17_6540M_E2_X_EC_1?$PDP_MAIN_CAR_SM$&fmt=webp');
INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('7', 'Red Coat', 'https://img.abercrombie.com/is/image/anf/KIC_144-1509-1016-501_model4?policy=product-large');
INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('8', 'Leather Bag', 'https://www.prada.com/content/dam/pradabkg_products/1/1BD/1BD311/ZO6F02SB/1BD311_ZO6_F02SB_V_OOO_SLF.jpg/jcr:content/renditions/cq5dam.web.hebebed.1000.1000.crop.jpg');
INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('9', 'Saffiano Bag', 'https://www.prada.com/content/dam/pradabkg_products/1/1BD/1BD318/NZVF0377/1BD318_NZV_F0377_V_CTO_SLF.jpg/jcr:content/renditions/cq5dam.web.hebebed.1000.1000.crop.jpg');
INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('10', 'Polo T-shirt', 'https://www.rlmedia.io/is/image/PoloGSI/s7-1416944_alternate10?$rl_df_zoom_a10$');
INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('11', 'Black Jeans', 'https://www.yoox.com/images/items/13/13737798DR_14_r.jpg?impolicy=crop&width=387&height=490');
INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('12', 'Black Coat', 'https://saint-laurent.dam.kering.com/m/1a89c49b1f436d8d/Medium-552492Y177W1000_A.jpg?v=3');
INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('13', 'White Jeans', 'https://media.tedbaker.com/t_pdp_dt_xlg_3-5m,f_auto/Product/Womens/263872_WHITE_1');
INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('14', 'White bag', 'https://images.lvrcdn.com/Big72I/5CK/045_341b9716-4acb-4984-bfa9-5f5d4c04c74a.JPG');
INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('15', 'Polo T-Shirt', 'https://img.modivo.cloud/product(a/c/6/0/ac605d477b15f2a192fc9d64ce2d7abd4d2ec358_1_8001000369106.jpg,jpg)/lacoste-polo-l1212-kokkino-classic-fit.jpg');
INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('16', 'Polo T-Shirt', 'https://lzd-img-global.slatic.net/g/p/da411c57e17c4ae646211cfe57d336de.jpg_720x720q80.jpg_.webp');
INSERT INTO ProductImage (productID, imageTitle, imageURL) VALUES ('17', 'Jacquemus Le Grand Chiquito leather bag', 'https://www.farfetch.com/gr/shopping/women/jacquemus-le-chiquito-moyen-mini-bag-item-18684545.aspx?size=17&storeid=9383&utm_source=google&utm_medium=cpc&utm_keywordid=114637642&utm_shoppingproductid=18684545-17&pid=google_search&af_channel=Search&c=2000554717&af_c_id=2000554717&af_siteid=&af_keywords=pla-385541723701&af_adset_id=76912255411&af_ad_id=353242755544&af_sub1=114637642&af_sub5=18684545-17&is_retargeting=true&shopping=yes&gclid=CjwKCAiAy_CcBhBeEiwAcoMRHMM6BB4N6S5Ua5Gn7Ou8WS6o8lvvKcYtqbcIAC7Jc6R4fLPd-nnR_hoCom8QAvD_BwE');

INSERT INTO FavoriteProduct (userID, productID) VALUES ('1', '14');
INSERT INTO FavoriteProduct (userID, productID) VALUES ('1', '9');
INSERT INTO FavoriteProduct (userID, productID) VALUES ('1', '8');
INSERT INTO FavoriteProduct (userID, productID) VALUES ('1', '5');
INSERT INTO FavoriteProduct (userID, productID) VALUES ('4', '4');
INSERT INTO FavoriteProduct (userID, productID) VALUES ('4', '7');
INSERT INTO FavoriteProduct (userID, productID) VALUES ('4', '12');
INSERT INTO FavoriteProduct (userID, productID) VALUES ('5', '1');
INSERT INTO FavoriteProduct (userID, productID) VALUES ('5', '2');
INSERT INTO FavoriteProduct (userID, productID) VALUES ('5', '4');
INSERT INTO FavoriteProduct (userID, productID) VALUES ('5', '5');

INSERT INTO Cart (userID, productID) VALUES ('2', '3');
INSERT INTO Cart (userID, productID) VALUES ('2', '10');
INSERT INTO Cart (userID, productID) VALUES ('3', '10');
INSERT INTO Cart (userID, productID) VALUES ('3', '4');
INSERT INTO Cart (userID, productID) VALUES ('3', '6');







