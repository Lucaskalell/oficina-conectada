import 'package:flutter_test/flutter_test.dart';
import 'package:oficina_conectada_front/models/produto_model.dart';

void main() {
  group('ProdutoModel.fromJson', () {
    test('converte os valores numéricos vindos da API', () {
      final produto = ProdutoModel.fromJson({
        'id': 3,
        'nome': 'Filtro de óleo',
        'precoCusto': 18,
        'precoVenda': 32.5,
        'quantidadeEmEstoque': 12,
      });

      expect(produto.id, 3);
      expect(produto.precoCusto, 18.0);
      expect(produto.precoVenda, 32.5);
      expect(produto.quantidadeEmEstoque, 12);
      expect(produto.descricao, isNull);
    });

    test('usa valores padrão quando campos vêm nulos', () {
      final produto = ProdutoModel.fromJson({});

      expect(produto.id, 0);
      expect(produto.nome, '');
      expect(produto.precoVenda, 0.0);
      expect(produto.quantidadeEmEstoque, 0);
    });

    test('toJson devolve os mesmos valores', () {
      final json = {
        'id': 1,
        'nome': 'Pastilha de freio',
        'descricao': 'Dianteira',
        'precoCusto': 60.0,
        'precoVenda': 120.0,
        'quantidadeEmEstoque': 4,
      };

      expect(ProdutoModel.fromJson(json).toJson(), json);
    });
  });
}
