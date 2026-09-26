import 'dart:convert';
import 'package:oficina_conectada_front/core/api/api_client.dart';
import 'package:oficina_conectada_front/models/estoque_resumo_model.dart';
import 'package:oficina_conectada_front/models/sub_categoria_model.dart';
import 'package:oficina_conectada_front/models/produto_model.dart';

class EstoqueService {
  final ApiClient _api = ApiClient();

  Future<EstoqueResumoModel> buscarResumo() async {
    final response = await _api.get('/estoque/resumo');
    if (response.statusCode == 200) {
      return EstoqueResumoModel.fromJson(jsonDecode(response.body));
    }
    throw Exception('Falha ao carregar resumo: ${response.statusCode}');
  }

  Future<List<SubCategoriaModel>> buscarSubcategorias(int catId) async {
    final response = await _api.get('/estoque/categorias/$catId/subcategorias');
    if (response.statusCode == 200) {
      final List<dynamic> data = jsonDecode(response.body);
      return data.map((j) => SubCategoriaModel.fromJson(j)).toList();
    }
    throw Exception('Falha ao carregar subcategorias: ${response.statusCode}');
  }

  Future<List<ProdutoModel>> buscarProdutos(int subId) async {
    final response = await _api.get('/estoque/subcategorias/$subId/produtos');
    if (response.statusCode == 200) {
      final List<dynamic> data = jsonDecode(response.body);
      return data.map((j) => ProdutoModel.fromJson(j)).toList();
    }
    throw Exception('Falha ao carregar produtos: ${response.statusCode}');
  }

  Future<void> criarProduto(ProdutoModel produto, int subId) async {
    final response = await _api.post('/estoque/subcategorias/$subId/produtos', body: produto.toJson());
    if (response.statusCode != 200 && response.statusCode != 201) {
      throw Exception('Falha ao criar produto: ${response.statusCode}');
    }
  }

  Future<void> atualizarProduto(ProdutoModel produto) async {
    final response = await _api.put('/estoque/produtos/${produto.id}', body: produto.toJson());
    if (response.statusCode != 200 && response.statusCode != 204) {
      throw Exception('Falha ao atualizar produto: ${response.statusCode}');
    }
  }

  Future<void> deletarProduto(int id) async {
    final response = await _api.delete('/estoque/produtos/$id');
    if (response.statusCode != 200 && response.statusCode != 204) {
      throw Exception('Falha ao excluir produto: ${response.statusCode}');
    }
  }
}
