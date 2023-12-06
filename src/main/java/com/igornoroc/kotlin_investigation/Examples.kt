package com.igornoroc.kotlin_investigation


/**
 * Example of data class
 */
data class User(val id: Int, val name: String, val email: String)

fun main() {
    val user = User(id = 1, name = "John Doe", email = "john@example.com")
    println(user)
}


/**
 * String interpolation or Concatenation
 */
fun main() {
    var name = "Setronica"
    println("$name from kotlin!")       //  result : Setronica from kotlin!




    var newHello = """
        Hello                                       
                   $name                          
                               from kotlin!       
    """.trimIndent()
    println(newHello)

    // result :
    //                       Hello
    //                               Setronica
    //                                            from kotlin!
}

/**
 * Interoperability and Code Transferability
 */

// JavaClass.java
public class JavaClass {
    private String message;

    public JavaClass(String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println(message);
    }
}

// KotlinClass.kt
fun main() {
    val javaObject = JavaClass("Hello from Java")
    javaObject.printMessage()

    val kotlinObject = KotlinClass("Hello from Kotlin")
    kotlinObject.printMessage()
}

// KotlinClass.kt
class KotlinClass(private val message: String) {
    fun printMessage() {
        println(message)
    }
}


/**
 * Extension Functions
 */

fun main() {
    var text = "Company"

    println("${text.extentExtension()}") // result : Hello from  Setronica!!!
}

fun String.replaceNameOfCompany(): String {
    return if (this.contains("Company")) this.replace(this, "It is Setronica!!!") else this
}

fun String.extentExtension(): String {
    return this.replaceNameOfCompany().replace("It is", "Hello from ")
}


/**
 * Security and Reliability
 */
fun main() {
    var text : String = "Hello Setronica!"

    val length: Int = text.length // it's ok

    var nullValue : String? = null

    val canBeNullLength : Int = nullValue.length  // please check the code!

    val canBeNullLengthSafety : Int? =  nullValue?.length
}


/**
 * Completable future hell
 */
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureHellWithErrorHandlingExample {

    public static void main(String[] args) {
        CompletableFuture<Void> resultFuture = CompletableFuture
                .supplyAsync(() -> performAsyncOperation("Operation 1"))
        .thenCompose(result1 -> CompletableFuture.supplyAsync(() -> performAsyncOperation("Operation 2")))
        .thenCompose(result2 -> CompletableFuture.supplyAsync(() -> performAsyncOperation("Operation 3")))
        .exceptionally(throwable -> {
            System.err.println("Error occurred: " + throwable.getMessage());
            return "Default Result";
        })
        .thenAccept(finalResult -> System.out.println("Final Result: " + finalResult));

        try {
            resultFuture.get(); // Блокируемся, чтобы подождать завершения всех CompletableFuture
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static String performAsyncOperation(String operation) {
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                if (Math.random() < 0.5) {
                    throw new RuntimeException("Operation failed");
                }
                String result = operation + " completed";
                future.complete(result);
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            } catch (RuntimeException e) {
                future.completeExceptionally(e);
            }
        }).start();
        return future.join();
    }
}


/**
 * Coroutines
 */

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun performAsyncOperation(operation: String): String {
    delay(1000) // Эмулируем асинхронную операцию
    if (Math.random() < 0.5) {
        throw RuntimeException("Operation failed")
    }
    return "$operation completed"
}

fun main() = runBlocking {
    val time = measureTimeMillis {
        try {
            val result1 = async { performAsyncOperation("Operation 1") }
            val result2 = async { performAsyncOperation("Operation 2") }
            val result3 = async { performAsyncOperation("Operation 3") }

            val finalResult = result1.await() + ", " + result2.await() + ", " + result3.await()
            println("Final Result: $finalResult")
        } catch (e: Exception) {
            println("Error occurred: ${e.message}")
        }
    }

    println("Execution time: $time ms")
}







