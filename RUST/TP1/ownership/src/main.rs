

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
    println!("Perimetre pour un rectangle de {}, {} avec la deuxieme fonction : {}", my_rectangle.length, my_rectangle.width, a);
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

fn print_reference() {
    let x = 18;

    let _ref1 = &x;
    let _ref2 = &x;

    let mut ref3 = &x;
    let mut ref4 = &x;

    println!("{}, {}", ref3, ref4);
}

fn swap(nombre1 : &mut f64, nombre2: &mut f64) -> () {
    let temp = *nombre2;
    *nombre2 = *nombre1;
    *nombre1 = temp;
}