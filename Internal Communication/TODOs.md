On this file, we will be occasionally making a list of the tasks that have priority and should be
performed.

Please have a look at the tasks below, and update their status with an "❌" or "✔️", depending on 
whether they have been completed or not.

<h1>TODOs:</h1>

1. The structure of the project is a bit weird. There are two rest-api folders, for no specific reason.
Please make it so that only one rest-api folder exists, by removing the one that is redundant. ✔️
2. Is TestOne.txt a file that is needed? If not, please remove from the repository. ✔️
3. The following files are redundant and may be deleted: mvnw, mvnw.cmd. They are maven wrappers
which allow somebody to run a Maven project, without having Maven installed, but this will not be
necessary on our case. ✔️
4. Make two packages under data, one called entities, and one called repositories. Move the classes
you have created to the corresponding package. ✔️
5. Please upload the ER diagram on the project. ✔️
6. Very nice job on creating schema.sql ✔️
7. I would add a bit of more variety on the credits column of the "User" table on data.sql, so
that you can instantly experiment with multiple values. ✔️
8. I would add some products that are similar and have the same SKU value on the "Product" table, on
data.sql. This will allow you to test grouping with SKU later on. ✔️
9. I would also add more than one images for some products, on the "ProductImage" table. This will
allow you to validate the one-to-many association from the "Product", to the "ProductImage". ✔️
10. Overall, really awesome job on data.sql. Nice! (The values were really fun too :P) ✔️    
11. Please, have a look at what Lombok is. It basically is a library, that allows you to generate
Getters and Setters with just one annotation. You will like it, and you may use it on your Entities
that have Setters and Getters for their values. ✔️
12. On "Product.java", line 30, there is a random Getter in-between field values. Please move that
below to where the rest of the getters are (Or use Lombok). ✔️
13. Next time, let's discuss the FavoriteProduct and Cart Entities together, and how their associations
should work. ✔️
14. Add ";database_to_upper=false" on the end of your jdbc URL, on application.properties. This will
disable the automatic capitalization of your tables on H2, and will make them look exactly the way 
that you have set them on schema.sql. ✔️
15. Under test/java/gr/dresso/rest/data, I have created some tests that you can run and validate
that your repositories (and thus, your entities) work. Right now they are not working, because
there is an issue with Hibernate and for some reason it uses lower case names for the tables,
instead of those that you have declared. For example, it uses "cart", instead of "Cart." Try
executing one of the tests by just clicking on the play button, from inside the class of the test, 
and seeing the error yourself. One simple solution to this, would be to change all @Table annotations
to also use lowercase names, and also update your schema.sql and data.sql to do that. Another solution,
so that we keep the names as they are, would be to add the following property on application.properties:
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl.
Instead of "." for the name of the property, you should use the ":", like the rest of the properties. For the
value of the property, please leave it as is. Please try executing the tests before you add this property, and after
in order to see that the error changes. ✔️
16. After you complete (15), you may notice that the new error is that column "id" does not exist. This is because 
you have called id columns "ID" on the database. To fix this, please add @Column("ID") annotations on all id fields of your
entities. ✔️