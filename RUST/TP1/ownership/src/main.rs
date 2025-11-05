

fn main() {
    let x = 3.0;
    let y = 4.0;

    println!("La moyenne des nombres {} et {} est : {}", x, y, average(x, y));

    let my_rectangle = Rectangle {
        length: x,
        width: y,
    };

    println!("Perimetre pour un rectangle de {} sur {} : {}", my_rectangle.width, my_rectangle.length, perimeter(&my_rectangle));
    let a = perimeter2(my_rectangle);
    println!("Perimeter2 a: {}", a);
}

fn average(n1: f64, n2: f64) -> f64 {
    (n1 + n2)/2.0
}

#[derive(Clone, Copy)]
struct Rectangle {
    length: f64,
    width: f64,
}

fn perimeter(rect : &Rectangle) -> f64 {
    (rect.length+rect.width)*2.0
}

fn perimeter2(rect : Rectangle) -> f64 {
    (rect.length+rect.width)*2.0
}